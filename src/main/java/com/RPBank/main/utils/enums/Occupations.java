package com.RPBank.main.utils.enums;

public enum Occupations {
        SALARIED,
        SELF_EMPLOYED,
        BUSINESS_OWNER,
        FARMER,
        STUDENT,
        RETIRED,
        HOUSEWIFE,
        UNEMPLOYED,
        GOVERNMENT_EMPLOYEE,
        PRIVATE_EMPLOYEE,
        PROFESSIONAL,
        DOCTOR,
        ENGINEER,
        TEACHER,
        LAWYER,
        FREELANCER,
        TRADER,
        CONSULTANT,
        BANKER,
        POLICE_OFFICER,
        DEFENSE_PERSONNEL,
        SOCIAL_WORKER,
        POLITICIAN,
        JOURNALIST,
        OTHERS;

        @Override
        public String toString() {
                return name();
        }

}
