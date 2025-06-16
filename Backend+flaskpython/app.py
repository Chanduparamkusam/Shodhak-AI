from flask import Flask, request, jsonify
from datetime import datetime, timedelta

app = Flask(__name__)

@app.route('/detect-return-abuse', methods=['POST'])
def detect_return_abuse():
    data = request.get_json()
    if not isinstance(data, list):
        return jsonify({"error": "Expected a list of return items"}), 400

    results = []

    for item in data:
        user_id = item.get("userId", "")
        order_id = item.get("orderId", "")
        product_id = item.get("productId", "")
        return_reason = item.get("returnReason", "").lower()
        return_type = item.get("returnType", "").lower()
        category = item.get("category", "").lower()
        return_date_str = item.get("returnDate", "")
        order_date_str = item.get("orderDate", "")

        suspicious_score = 0.0
        reasons = []

        # Parse dates
        try:
            return_date = datetime.strptime(return_date_str, "%Y-%m-%d")
        except:
            return_date = None

        try:
            order_date = datetime.strptime(order_date_str, "%Y-%m-%d")
        except:
            order_date = None

        # Business logic
        if order_date and return_date:
            return_window_days = 7
            window_end = order_date + timedelta(days=return_window_days)
            days_to_expiry = (window_end - return_date).days
            days_since_order = (return_date - order_date).days

            if return_type == "refund" and 0 <= days_to_expiry <= 2:
                suspicious_score += 0.7
                reasons.append("Refund near return window expiry.")

            if return_type == "replacement" and category in ["electronics", "home_appliance", "appliance"]:
                suspicious_score += 0.6
                reasons.append("Replacement of expensive category.")

            if days_since_order > return_window_days:
                suspicious_score += 0.9
                reasons.append("Return date exceeds return window (7 days).")

        suspicious = suspicious_score >= 0.5
        if not reasons:
            reasons.append("No suspicious return behavior detected.")

        results.append({
            "userId": user_id,
            "orderId": order_id,
            "productId": product_id,
            "suspicious": suspicious,
            "reason": " ".join(reasons),
            "suspiciousScore": round(suspicious_score, 2)
        })

    return jsonify(results)

# ----- Fake Review Detection -----

fake_reviews_data = [
    {
        "userId": "user001",
        "productId": "prod789",
        "review": "This product is amazing!",
        "suspicious": False,
        "message": "Looks like a genuine review"
    },
    {
        "userId": "user002",
        "productId": "prod123",
        "review": "Worst ever. I got scammed. Highly recommended!",
        "suspicious": True,
        "message": "Contradictory sentiment. Might be fake."
    }
]

@app.route('/fake-review', methods=['GET'])
def get_fake_reviews():
    return jsonify(fake_reviews_data)

@app.route('/detect-fake-review', methods=['POST'])
def detect_fake_review():
    data = request.get_json()
    review_text = data.get("review", "")
    user_id = data.get("userId", "")
    product_id = data.get("productId", "")

    if not review_text or not review_text.strip():
        return jsonify({
            "userId": user_id,
            "productId": product_id,
            "fake": False,
            "score": 0.0,
            "message": "Review text is empty."
        }), 400

    suspicious_score = 0.0
    reasons = []

    if len(review_text.split()) < 5:
        suspicious_score += 0.6
        reasons.append("Very short review.")

    if any(review_text.lower().count(word) >= 3 for word in ["good", "best", "amazing"]):
        suspicious_score += 0.5
        reasons.append("Repetitive praise words.")

    if "refund" in review_text.lower() or "return" in review_text.lower():
        suspicious_score += 0.4
        reasons.append("Mentions refund/return.")

    suspicious = suspicious_score > 0.5
    if not reasons:
        reasons.append("No suspicious pattern detected.")

    return jsonify({
        "userId": user_id,
        "productId": product_id,
        "review": review_text,
        "fake": suspicious,
        "message": " ".join(reasons),
        "score": round(suspicious_score, 2)
    })

import os

if __name__ == "__main__":
    port = int(os.environ.get("PORT", 5000))
    app.run(host="0.0.0.0", port=port, debug=True)
