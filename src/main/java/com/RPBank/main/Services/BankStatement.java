package com.RPBank.main.Services;

import com.RPBank.main.Authenticator.ValidateAccountDetails;
import com.RPBank.main.Beans.Transaction;
import com.RPBank.main.Beans.User;
import com.RPBank.main.DAO.TransactionDAO;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.EmailDetails;
import com.RPBank.main.DTO.TransactionRequests.StatementRequest;
import com.RPBank.main.DTO.ValidationResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BankStatement {

    @Autowired
    private final TransactionDAO transactionDAO;

    @Autowired
    private final UserDAO userDAO;

    private final EmailService emailService;

    private static final String FILE = "C:\\Users\\Acer\\Desktop\\HackLoom\\MyProject\\Banking Application\\RPBank\\src\\main\\resources\\static\\statement.pdf";

    public List<Transaction> generateStatement(StatementRequest request) throws Exception {

        ResponseEntity<ValidationResponse> validateAccountResponse = ValidateAccountDetails.validateAccountNumber(userDAO, request.getAccountNumber());
        if (!validateAccountResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new Exception("Invalid account number!");
        }

        LocalDate start = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(request.getEndDate(), DateTimeFormatter.ISO_DATE);

        List<Transaction> transactions = transactionDAO.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(request.getAccountNumber()))
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getCreatedAt().toLocalDate(); // Convert LocalDateTime to LocalDate
                    return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
                })
                .collect(Collectors.toList());

        prepareStatement(transactions , request);

        return transactions;
    }

    public void prepareStatement(List<Transaction> transactions , StatementRequest request) throws DocumentException {
        log.info("Starting PDF generation for {} transactions.", transactions.size());

        Rectangle pageSize = new Rectangle(PageSize.A4);
        Document document = new Document(pageSize);

        User user = userDAO.findByAccountDTO_AccountNumber(request.getAccountNumber());

        try (OutputStream outputStream = new FileOutputStream(FILE)) {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            log.debug("PDF document opened successfully.");

            log.info("Writing transaction data to PDF...");

            PdfPTable bankInfo = new PdfPTable(1);
            PdfPCell bankName = new PdfPCell(new Phrase("RP Bank"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.BLUE);
            bankName.setPadding(22f);

            PdfPCell bankAddress = new PdfPCell(new Phrase("23 Howrah"));
            bankAddress.setBorder(0);

            bankInfo.addCell(bankName);
            bankInfo.addCell(bankAddress);

            PdfPTable statementInfo = new PdfPTable(2);

            PdfPCell customerInfo = new PdfPCell(new Phrase("start date : " + request.getStartDate()));
            customerInfo.setBorder(0);

            PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT : " ));
            statement.setBorder(0);

            PdfPCell endDate = new PdfPCell(new Phrase("End Date : " + request.getEndDate()));
            statement.setBorder(0);


            PdfPCell accountName = new PdfPCell(new Phrase("Name of Account holder : " + user.getAccountDTO().getAccountName()));
            statement.setBorder(0);

            PdfPCell space = new PdfPCell();

            PdfPCell address = new PdfPCell(new Phrase("Address : " + user.getCurrentAddress()));
            statement.setBorder(0);

            PdfPTable transactionsTable = new PdfPTable(4);

            PdfPCell date = new PdfPCell(new Phrase("Date"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.BLUE);
            bankName.setPadding(5f);

            PdfPCell type = new PdfPCell(new Phrase("Transaction Type"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.BLUE);
            bankName.setPadding(5f);

            PdfPCell amount = new PdfPCell(new Phrase("Amount"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.BLUE);
            bankName.setPadding(5f);

            PdfPCell status = new PdfPCell(new Phrase("Status"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.BLUE);
            bankName.setPadding(5f);

            transactionsTable.addCell(date);
            transactionsTable.addCell(type);
            transactionsTable.addCell(amount);
            transactionsTable.addCell(status);

            transactions.forEach(transaction -> {
                transactionsTable.addCell(transaction.getCreatedAt().toString());
                transactionsTable.addCell(transaction.getTransactionType().toString());
                transactionsTable.addCell(transaction.getAmount().toString());
                transactionsTable.addCell(transaction.getStatus().toString());
            });

            statementInfo.addCell(customerInfo);
            statementInfo.addCell(statement);
            statementInfo.addCell(endDate);
            statementInfo.addCell(accountName);
            statementInfo.addCell(space);
            statementInfo.addCell(address);

            document.add(bankInfo);
            document.add(statementInfo);
            document.add(transactionsTable);


            document.close();
            log.info("PDF generation completed. File saved as '{}'.", FILE);

            EmailDetails mail = EmailDetails.builder()
                    .recipient(user.getPrimaryEmail())
                    .subject("Your RP Bank Account Statement")
                    .messageBody(
                            "Dear " + user.getAccountDTO().getAccountName() + ",\n\n" +
                                    "Please find attached your account statement as per your request.\n\n" +
                                    "If you have any questions or require further assistance, feel free to contact our customer support.\n\n" +
                                    "Thank you for banking with RP Bank.\n\n" +
                                    "Best regards,\n" +
                                    "RP Bank Customer Service Team"
                    )
                    .attachment(FILE)
                    .build();


            emailService.sendEmailWithAttachment(mail);

        }
        catch (FileNotFoundException e) {
            log.error("File not found during PDF generation: {}", e.getMessage(), e);
            throw new RuntimeException("PDF generation failed: File not found.", e);
        }
        catch (IOException e) {
            log.error("I/O exception during PDF generation: {}", e.getMessage(), e);
            throw new RuntimeException("PDF generation failed: I/O error.", e);
        }
    }
}
