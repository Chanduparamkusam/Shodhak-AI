import pandas as pd
from textblob import TextBlob

# Sample data (can later be connected to DB or API)
data = {
    "review_id": [1, 2, 3],
    "review_text": [
        "This product is excellent, highly recommend it!",
        "Worst purchase ever. Do not buy!",
        "Buy now! Best ever! Best ever! Best ever! Limited time!"
    ]
}

df = pd.DataFrame(data)

# Function to detect fake reviews based on repetition and sentiment
def detect_fake_reviews(text):
    analysis = TextBlob(text)
    polarity = analysis.sentiment.polarity
    word_count = len(text.split())
    repeated_phrases = text.lower().count("best ever") > 2

    if repeated_phrases or word_count < 5 or polarity > 0.9:
        return True
    return False

# Apply the detection
df['is_fake'] = df['review_text'].apply(detect_fake_reviews)

print(df)
