
# 🔎 Shodhak AI – Trust & Safety Dashboard

**Shodhak AI** is an AI-powered Trust & Safety system for e-commerce platforms.  
It detects return abuse and fake reviews using intelligent scoring and machine learning.

🚀 **Live Demo:** _Coming soon with Cooler e-commerce site integration_

## 📑 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [How It Works](#how-it-works)
- [Installation](#installation)
- [Screenshots](#screenshots)
- [Contact](#contact)

## ✨ Features

- 🛡️ **Return Abuse Detection** – Flags suspicious return behaviors using AI-powered logic  
- 💬 **Fake Review Detection** – Detects fake reviews with sentiment and pattern analysis  
- 📊 **Analytics (Coming Soon)** – Real-time abuse and review insights dashboard  
- 🔒 **JWT Security** – Secure authentication and role-based access control  
- ⚙️ **Modular Microservices** – Backend in Spring Boot (Java) & Python Flask  
- 🌐 **Responsive UI** – Built with HTML, CSS, and JavaScript for a sleek UX  

## 🛠 Tech Stack

**Frontend:**  
- HTML5, CSS3, JavaScript  
- Pure CSS design (no frameworks)  
- Live splash screen and tab-based UI  

**Backend:**  
- Spring Boot (Java) – Core microservice for user and abuse management  
- Python Flask – AI service for detecting fake reviews and suspicious returns  

**Security & Auth:**  
- JWT-based Authentication  
- Role-based Authorization (Admin/User)  
- OTP Verification via Email/Mobile (planned in Cooler e-commerce)  

**Tools & Platforms:**  
- Eclipse IDE, VS Code  
- Postman (API testing)  
- Git & GitHub for version control  

## 🔍 How It Works

1. Users register and log in via the frontend UI, secured by JWT authentication.  
2. Return data or reviews are submitted through the UI to the backend.  
3. The Spring Boot backend validates data and forwards relevant requests to the Python Flask AI microservice.  
4. The AI microservice analyzes inputs for suspicious behavior or fake reviews using machine learning and sentiment analysis.  
5. Results (scores, flags, reasons) are sent back and displayed to the user in real-time.  
6. Admins can access dashboards (coming soon) for analytics and monitoring.  

## 🚀 Installation

### 1. Clone the Repository

Open your terminal and run:

```bash
git clone https://github.com/your-username/shodhak-ai.git
cd shodhak-ai
```

*Replace `your-username` with your GitHub username.*

### 2. Run Spring Boot Backend

In the same terminal, run:

```bash
cd shodhak-ai-backend
mvn clean install
mvn spring-boot:run
```

The backend will start at [http://localhost:8089](http://localhost:8089). Keep this terminal open.

### 3. Run Python Flask AI Microservice

Open a new terminal window, then run:

```bash
cd shodhak-ai/shodhak-ai-flask
```

Create and activate a Python virtual environment:

- On Windows:

```bash
python -m venv venv
venv\Scripts\activate
```

- On Linux/macOS:

```bash
python3 -m venv venv
source venv/bin/activate
```

Then install dependencies and run Flask:

```bash
pip install -r requirements.txt
python app.py
```

Flask service will start at [http://localhost:5000](http://localhost:5000). Keep this terminal open.

### 4. Open Frontend

Open the `frontend` folder in your file explorer.

Double-click `index.html` to open it in your browser.

---

## Screenshots

#returnabuse
screenshot1:  [https://github.com/Chanduparamkusam/Shodhak-AI/blob/main/screenshots/Screenshot%202025-06-13%20200416%20return%20abuse.png]

  screenshot2  [https://github.com/Chanduparamkusam/Shodhak-AI/blob/main/screenshots/Screenshot%202025-06-13%20200757%20%20return%20fraud.png]

 #reviewfraud
 screenshot1:[https://github.com/Chanduparamkusam/Shodhak-AI/blob/main/screenshots/Screenshot%202025-06-13%20200441%20review%20fraud.png]

 screenshot2 :[https://github.com/Chanduparamkusam/Shodhak-AI/blob/main/screenshots/screenshotreview.png]

## Contact

For questions, issues, or feedback, please contact:

- Paramkusam Chandu Gopala Krishna
- Email: chanduparamkusam@gmail.com  
- GitHub: [your-username](https://github.com/Chanduparamkusam)

---

*Happy coding! 🚀*

---

## ⚠️ Legal Notice

This project "Shodhak AI" is built by **Paramkusam Chandu Gopala Krishna** and is publicly available for educational and hiring purposes only.  
Unauthorized copying, cloning, or commercial use of this codebase without proper credit or permission is strictly prohibited.

MIT License applies.

