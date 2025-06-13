window.addEventListener("load", () => {
  const splash = document.getElementById("splashScreen");

  // Wait 5 seconds before fading out
  setTimeout(() => {
    splash.style.opacity = 0;
    splash.style.transition = "opacity 2s ease";

    setTimeout(() => {
      splash.style.display = "none";
    }, 1000);
  }, 5000);
});

// Tab switching
function showTab(tabId, event) {
  document.querySelectorAll(".tab-content").forEach(div => div.classList.remove("active"));
  document.querySelectorAll(".tab-btn").forEach(btn => btn.classList.remove("active"));
  document.getElementById(tabId).classList.add("active");
  event.target.classList.add("active");

  // Optionally load analytics data when Analytics tab is shown
  if (tabId === "analyticsTab") {
    loadAnalytics();
  }
}

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

  fetch("http://localhost:8089/api/return-abuse/detect", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(requestBody)
  })
    .then(res => res.json())
    .then(result => {
      document.getElementById("suspiciousResult").textContent = result.suspicious;
      document.getElementById("reasonResult").textContent = result.reason;
      document.getElementById("scoreResult").textContent = result.suspiciousScore;
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
    const response = await fetch("http://localhost:8089/api/reviews/analyze", {
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

// === Fill Sample Data ===
function fillReturnSample() {
  document.getElementById("userId").value = "u123";
  document.getElementById("orderId").value = "o456";
  document.getElementById("productId").value = "p789";
  document.getElementById("reason").value = "Item did not match description";
  document.getElementById("returnType").value = "Refund";
  document.getElementById("category").value = "Electronics";
  document.getElementById("orderDate").value = "2024-05-01";
  document.getElementById("returnDate").value = "2024-05-07";
}

function fillReviewSample() {
  document.getElementById("fakeUserId").value = "101";
  document.getElementById("fakeProductId").value = "202";
  document.getElementById("reviewText").value = "Best product ever! Super amazing!!!";
  document.getElementById("rating").value = "5";
}

// === Load Analytics (static or dynamic) ===
function loadAnalytics() {
  // Static values for now
  document.getElementById("abuseCount").textContent = "124";
  document.getElementById("reviewCount").textContent = "78";
  document.getElementById("accuracyScore").textContent = "92.4%";

 
}
