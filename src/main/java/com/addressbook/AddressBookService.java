package com.addressbook;

import java.util.List;

public class AddressBookService {

    public enum IOService {DB_IO,}

    private List<AddressBookData> addressBookList;
    private AddressBookDBService addressBookDBService;

    public AddressBookService() {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public AddressBookService(List<AddressBookData> addressBookList) {
        this.addressBookList = addressBookList;
    }

    public List<AddressBookData> readAddressBookData(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.addressBookList = addressBookDBService.readData();
        return this.addressBookList;
    }

    public boolean checkAddressBookInSyncWithDB(String name) {
        List<AddressBookData> addressBookDataList = addressBookDBService.getAddressBookData(name);
        return addressBookDataList.get(0).equals(getAddressBookData(name));
    }

    public void updateContactAddress(String name, String address) {
        int result = addressBookDBService.updateContactAddress(name, address);
        if (result == 0) return;
        AddressBookData addressBookData = this.getAddressBookData(name);
        if(addressBookData != null) addressBookData.address = address;
    }
    private AddressBookData getAddressBookData(String name) {
        return this.addressBookList.stream()
                .filter(addressBookDataItem -> addressBookDataItem.name.equals(name))
                .findFirst()
                .orElse(null);
    }

}
