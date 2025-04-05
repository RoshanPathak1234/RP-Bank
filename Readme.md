# 🏦 RP Bank – API Documentation

Welcome to the API documentation for **RP Bank**. This documentation provides a comprehensive Swagger UI interface to explore and test all available backend endpoints developed for RP Bank's digital banking system.

---

## 📘 Access Documentation

Access the live API documentation via Swagger UI at:

👉 [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

---

## 🛠️ How to Run the Project

1. Clone the repository and navigate to the project directory.
2. Run the backend application using your preferred IDE or by executing:
   ```bash
   mvn spring-boot:run
   ```
3. Ensure the backend is running on **port 8080**.
4. Open your browser and visit:
   [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

---

## 🔐 Authentication & Authorization

- 🔒 Basic Authentication with role-based access control:
    - `CUSTOMER`: Regular banking user
    - `EMPLOYEE`: Bank staff
    - `ADMIN`: Full administrative privileges
- 🚫 Unauthorized and unauthenticated requests are restricted and return appropriate HTTP status codes.

---

## 📂 API Functionalities

### 🧾 Account Management
- Create new accounts with valid personal and KYC information
- Retrieve account details using account number
- Update customer information (e.g., phone number, email, address)
- Deactivate or close accounts with proper verification

### 💸 Transaction Services
- Deposit and withdraw funds securely
- Transfer funds between accounts (intra-bank)
- View full transaction history
- Filter transactions using:
    - ✅ Date range
    - ✅ Transaction type (DEBIT, CREDIT, TRANSFER)
    - ✅ Status (SUCCESS, FAILED, PENDING)

### 📄 Bank Statement
- Generate professional PDF statements
- Supports dynamic date ranges (e.g., past month, quarter)
- PDF includes:
    - Bank and customer info
    - Complete transaction summary
- Option to email the statement to the customer’s registered email

### 📤 Email Notifications
- Send automated email alerts for:
    - New account creation
    - Deposits/withdrawals
    - Fund transfers
- Email professionally formatted PDF bank statements
- Secure and formal email content

### 🔍 Search & Filters
- Search user by:
    - Account number
    - Registered email
- Transaction filtering with multiple criteria:
    - Date
    - Amount threshold
    - Transaction status/type

---

## 🧰 Tools & Technologies Used

- **Java 17** / **Spring Boot 3**
- **Spring Security** for authentication and RBAC
- **Spring Data JPA** for ORM
- **Swagger UI / OpenAPI 3** for API documentation
- **Maven** as the build tool
- **iText 7** for PDF generation
- **JavaMailSender (Spring Mail)** for email functionality
- **Lombok** to reduce boilerplate code

---

## 🚀 Future Enhancements

- 📱 RESTful APIs for integration with mobile banking apps
- 🧠 AI-based fraud detection & anomaly alerts
- 📊 Graphical analytics dashboards for users and employees
- 🔐 Two-Factor Authentication (2FA) using OTP or authenticator apps
- 📥 User-friendly portal to download monthly/quarterly statements
- 💬 AI-powered chatbot for instant support and queries
- ⏰ Scheduled auto-emailing of bank statements
- 🧾 GST-compliant digital receipts and business statements
- 📎 Document upload and digital KYC for account creation

---

> 💡 Feel free to explore and test endpoints through Swagger UI. If you encounter issues or have suggestions, contact the RP Bank backend development team for assistance.

