package com.addressbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.addressbook.AddressBookService.IOService.DB_IO;

public class AddressBookTest {
    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactsCount(){
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(DB_IO);
        Assertions.assertEquals(3,addressBookData.size());
    }
    @Test
    public void givenNewAddress_WhenUpdated_ShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(DB_IO);
        addressBookService.updateContactAddress("Srinath", "Chennai");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Srinath");
        Assertions.assertTrue(result);
    }
    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchContactsCount(){
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(DB_IO);
        LocalDate startDate = LocalDate.of(2020,01,01);
        LocalDate endDate = LocalDate.now();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookForDateRange(DB_IO, startDate, endDate);
        Assertions.assertEquals(3, addressBookData.size());
    }
}
