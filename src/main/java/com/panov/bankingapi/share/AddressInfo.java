package com.panov.bankingapi.share;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AddressInfo {
    @Column(
        name = "region",
        nullable = false,
        length = 63
    )
    private String region;
    @Column(
        name = "city",
        nullable = false,
        length = 63
    )
    private String city;
    @Column(
        name = "address",
        nullable = false
    )
    private String fullAddress;
    @Column(
        name = "postal_code",
        nullable = false,
        length = 5
    )
    private String postalCode;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
