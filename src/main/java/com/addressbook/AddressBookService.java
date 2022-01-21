package com.addressbook;

import java.util.List;

public class AddressBookService {

    public enum IOService {DB_IO,}

    private List<AddressBookData> addressBookList;

    public AddressBookService() {
    }

    public AddressBookService(List<AddressBookData> addressBookList) {
        this.addressBookList = addressBookList;
    }

    public List<AddressBookData> readAddressBookData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.addressBookList = new AddressBookDBService().readData();
        return this.addressBookList;
    }
}
