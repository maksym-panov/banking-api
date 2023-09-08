package com.panov.bankingapi.share;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

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

    public AddressInfo() {}

    public AddressInfo(
        String region, String city,
        String fullAddress, String postalCode
    ) {
        this.region = region;
        this.city = city;
        this.fullAddress = fullAddress;
        this.postalCode = postalCode;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressInfo that = (AddressInfo) o;
        return Objects.equals(region, that.region) &&
                Objects.equals(city, that.city) &&
                Objects.equals(fullAddress, that.fullAddress) &&
                Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, city, fullAddress, postalCode);
    }
}
