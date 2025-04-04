package com.RPBank.main.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Schema(name = "Address", description = "Holds address details including house number, area, city, state, country, and postal information.")
public class Address {

    @Schema(description = "House number of the address", example = "123/A")
    @Column(nullable = false)
    private String houseNumber;

    @Schema(description = "Locality or area name", example = "Park Street")
    @Column(nullable = false)
    private String area;

    @Schema(description = "Nearby landmark (optional)", example = "Near City Mall")
    private String landMark;

    @Schema(description = "Post Office", example = "Central PO")
    @Column(nullable = false)
    private String PO;

    @Schema(description = "Police Station", example = "Central PS")
    @Column(nullable = false)
    private String PS;

    @Schema(description = "City name", example = "Kolkata")
    @Column(nullable = false)
    private String city;

    @Schema(description = "State name", example = "West Bengal")
    @Column(nullable = false)
    private String state;

    @Schema(description = "Country name", example = "India")
    @Column(nullable = false)
    private String country;

    @Schema(description = "Postal code", example = "700016")
    @Column(nullable = false)
    private String pinCode;

    @Override
    public String toString() {
        return String.format(
                "Address { houseNumber='%s', area='%s', landMark='%s', PO='%s', PS='%s', city='%s', state='%s', country='%s', pinCode='%s' }",
                houseNumber, area, landMark, PO, PS, city, state, country, pinCode
        );
    }
}
