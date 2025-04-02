package com.RPBank.main.utils.enums;

public class LegalDocuments {

    // Identity Proofs
    public static enum IdentityProof {
        AADHAAR_CARD,
        PAN_CARD,
        PASSPORT,
        VOTER_ID,
        DRIVING_LICENSE,
        RATION_CARD,
        NREGA_JOB_CARD;
    }

    // Address Proofs
    public static enum AddressProof {
        BANK_STATEMENT,
        UTILITY_BILL,
        RENT_AGREEMENT,
        PROPERTY_TAX_RECEIPT,
        GAS_CONNECTION_BILL;
    }

    // Financial Proofs
    public static enum FinancialProof {
        SALARY_SLIP,
        INCOME_TAX_RETURN,
        FORM_16,
        PENSION_BOOK,
        INVESTMENT_PROOF;
    }

    // Birth & Education Proofs
    public static enum BirthEducationProof {
        BIRTH_CERTIFICATE,
        SCHOOL_LEAVING_CERTIFICATE,
        DEGREE_CERTIFICATE,
        GOVERNMENT_ISSUED_CERTIFICATE;
    }

    // Employment & Legal Documents
    public static enum EmploymentLegalProof {
        COMPANY_ID,
        GOVERNMENT_ID,
        COURT_ORDER,
        POLICE_VERIFICATION_CERTIFICATE;
    }

    // Miscellaneous
    public static enum MiscellaneousProof {
        MEDICAL_INSURANCE_POLICY,
        VEHICLE_REGISTRATION_CERTIFICATE,
        DISABILITY_CERTIFICATE,
        OTHER;
    }
}
