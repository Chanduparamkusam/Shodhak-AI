window.addEventListener("load", () => {
  const splash = document.getElementById("splashScreen");

  setTimeout(() => {
    splash.style.opacity = 0;
    splash.style.transition = "opacity 2s ease";
    setTimeout(() => {
      splash.style.display = "none";
    }, 1000);
  }, 5000);
});

function showTab(tabId, event) {
  document.querySelectorAll(".tab-content").forEach(div => div.classList.remove("active"));
  document.querySelectorAll(".tab-btn").forEach(btn => btn.classList.remove("active"));
  document.getElementById(tabId).classList.add("active");
  event.target.classList.add("active");

  if (tabId === "analyticsTab") {
    loadAnalytics();
  }
}

const BASE_URL = "https://shodhakai-springboot.onrender.com";

// === Return Abuse Detection ===
document.getElementById("detectReturnBtn").addEventListener("click", () => {
  const requestBody = [{
    userId: document.getElementById("userId").value.trim(),
    returnWindowDays: 7,
    orders: [
      {
        orderId: document.getElementById("orderId").value.trim(),
        orderDate: document.getElementById("orderDate").value,
        products: [
          {
            productId: document.getElementById("productId").value.trim(),
            category: document.getElementById("category").value.trim(),
            returnDate: document.getElementById("returnDate").value,
            returnType: document.getElementById("returnType").value.trim(),
            reason: document.getElementById("reason").value.trim()
          }
        ]
      }
    ]
  }];

  fetch(`${BASE_URL}/api/return-abuse/detect`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(requestBody)
  })
    .then(res => res.json())
    .then(result => {
      const data = Array.isArray(result) ? result[0] : result;
      document.getElementById("suspiciousResult").textContent = data.suspicious;
      document.getElementById("reasonResult").textContent = data.reason || data.message;
      document.getElementById("scoreResult").textContent = data.suspiciousScore;
    })
    .catch(err => alert("Return Abuse Error:\n" + err));
});

// === Fake Review Detection ===
document.getElementById("detectFakeBtn").addEventListener("click", async () => {
  const userId = parseInt(document.getElementById("fakeUserId").value.trim());
  const productId = parseInt(document.getElementById("fakeProductId").value.trim());
  const reviewText = document.getElementById("reviewText").value.trim();
  const rating = parseInt(document.getElementById("rating").value.trim());

  if (isNaN(userId) || isNaN(productId) || !reviewText || isNaN(rating)) {
    return alert("Please enter numeric userId/productId, review text, and rating.");
  }

  const requestBody = {
    userId: userId,
    productId: productId,
    review: reviewText,
    rating: rating
  };

  try {
    const response = await fetch(`${BASE_URL}/api/reviews/analyze`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestBody)
    });

    if (!response.ok) {
      const errorMessage = await response.text();
      throw new Error(errorMessage || "Unknown error from server");
    }

    const data = await response.json();
    document.getElementById("fakeResult").textContent = data.fake;
    document.getElementById("fakeMessage").textContent = data.message;
    document.getElementById("fakeScore").textContent = data.score;
  } catch (error) {
    alert("Fake Review Error:\n" + error.message);
    console.error(error);
  }
});

// === Fill Return Sample Data ===
function fillReturnSample(index = 1) {
  const samples = [
    {
      userId: "u123",
      orderId: "o456",
      productId: "p789",
      reason: "Item did not match description",
      returnType: "Refund",
      category: "Electronics",
      orderDate: "2024-05-01",
      returnDate: "2024-05-07"
    },
    {
      userId: "u999",
      orderId: "o999",
      productId: "p999",
      reason: "Used and returned near expiry",
      returnType: "Refund",
      category: "Fashion",
      orderDate: "2024-06-01",
      returnDate: "2024-06-10"
    },
    {
      userId: "u777",
      orderId: "o777",
      productId: "p777",
      reason: "Wrong item delivered",
      returnType: "Replacement",
      category: "Home",
      orderDate: "2024-06-05",
      returnDate: "2024-06-08"
    }
  ];

  const sample = samples[index - 1];
  if (!sample) return alert("Invalid sample index");

  document.getElementById("userId").value = sample.userId;
  document.getElementById("orderId").value = sample.orderId;
  document.getElementById("productId").value = sample.productId;
  document.getElementById("reason").value = sample.reason;
  document.getElementById("returnType").value = sample.returnType;
  document.getElementById("category").value = sample.category;
  document.getElementById("orderDate").value = sample.orderDate;
  document.getElementById("returnDate").value = sample.returnDate;
}

// === Fill Review Sample Data ===
function fillReviewSample(index = 1) {
  const samples = [
    {
      userId: "101",
      productId: "202",
      reviewText: "Best product ever! Super amazing!!!",
      rating: 5
    },
    {
      userId: "555",
      productId: "888",
      reviewText: "Not good. Broke after two days. Total waste.",
      rating: 1
    },
    {
      userId: "333",
      productId: "444",
      reviewText: "It's okay, works fine, nothing special.",
      rating: 3
    }
  ];

  const sample = samples[index - 1];
  if (!sample) return alert("Invalid review sample index");

  document.getElementById("fakeUserId").value = sample.userId;
  document.getElementById("fakeProductId").value = sample.productId;
  document.getElementById("reviewText").value = sample.reviewText;
  document.getElementById("rating").value = sample.rating;
}

// === Analytics (static) ===
function loadAnalytics() {
  document.getElementById("abuseCount").textContent = "124";
  document.getElementById("reviewCount").textContent = "78";
  document.getElementById("accuracyScore").textContent = "92.4%";
}
