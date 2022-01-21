package com.addressbook;

import java.time.LocalDate;

public class AddressBookData {

    public int id;
    public String name;
    public String address;
    public String state;
    public LocalDate date;

    public AddressBookData(int id, String name, String address, String state) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.state = state;
    }

    public AddressBookData(int id, String name, String address, String state,LocalDate date) {
        this(id, name, address, state);
        this.date = date;
    }

    @Override
    public String toString() {
        return "AddressBookData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", date=" + date +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookData that = (AddressBookData) o;
        return id == that.id &&
                CharSequence.compare(that.address, address) == 0 &&
                name.equals(that.name);
    }
}
