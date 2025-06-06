package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packages {
    private SimpleIntegerProperty packageid;
    private SimpleStringProperty pkgname;
    private SimpleObjectProperty<LocalDate> pkgstartdate;
    private SimpleObjectProperty<LocalDate>  pkgenddate;
    private SimpleStringProperty pkgdesc;
    private SimpleIntegerProperty pkgbaseprice;
    private SimpleIntegerProperty pkgagencycommission;
    private SimpleStringProperty photo_url;
    private SimpleStringProperty destination;
    private SimpleStringProperty tags;

    public Packages(SimpleStringProperty pkgname, SimpleObjectProperty<LocalDate> pkgstartdate, SimpleObjectProperty<LocalDate> pkgenddate, SimpleStringProperty pkgdesc, SimpleIntegerProperty pkgbaseprice, SimpleIntegerProperty pkgagencycommission, SimpleStringProperty photoURL,
                    SimpleStringProperty destination, SimpleStringProperty tags) {
        this.pkgname = pkgname;
        this.pkgstartdate = pkgstartdate;
        this.pkgenddate = pkgenddate;
        this.pkgdesc = pkgdesc;
        this.pkgbaseprice = pkgbaseprice;
        this.pkgagencycommission = pkgagencycommission;
        this.photo_url = photoURL;
        this.destination = destination;
        this.tags = tags;
    }

    public Packages(SimpleIntegerProperty packageid, SimpleStringProperty pkgname, SimpleObjectProperty<LocalDate>  pkgstartdate,
                    SimpleObjectProperty<LocalDate>  pkgenddate, SimpleStringProperty pkgdesc, SimpleIntegerProperty pkgbaseprice,
                    SimpleIntegerProperty pkgagencycommission, SimpleStringProperty photoURL, SimpleStringProperty destination, SimpleStringProperty tags) {
        this.packageid = packageid;
        this.pkgname = pkgname;
        this.pkgstartdate = pkgstartdate;
        this.pkgenddate = pkgenddate;
        this.pkgdesc = pkgdesc;
        this.pkgbaseprice = pkgbaseprice;
        this.pkgagencycommission = pkgagencycommission;
        this.photo_url = photoURL;
        this.destination = destination;
        this.tags = tags;
    }


    public int getPackageid() {
        return packageid.get();
    }

    public void setPackageid(int packageid) {
        this.packageid = new SimpleIntegerProperty(packageid);
    }

    public SimpleIntegerProperty packageidProperty() {
        return packageid;
    }

    public String getPkgname() {
        return pkgname.get();
    }

    public SimpleStringProperty pkgnameProperty() {
        return pkgname;
    }

    public SimpleObjectProperty<LocalDate>  getPkgstartdate() {
        return pkgstartdate;
    }

    public SimpleObjectProperty<LocalDate>  getPkgenddate() {
        return pkgenddate;
    }

    public String getPkgdesc() {
        return pkgdesc.get();
    }

    public SimpleStringProperty pkgdescProperty() {
        return pkgdesc;
    }

    public int getPkgbaseprice() {
        return pkgbaseprice.get();
    }

    public SimpleIntegerProperty pkgbasepriceProperty() {
        return pkgbaseprice;
    }

    public int getPkgagencycommission() {
        return pkgagencycommission.get();
    }

    public SimpleIntegerProperty pkgagencycommissionProperty() {
        return pkgagencycommission;
    }

    public String getPhoto_url() {
        return photo_url.get();
    }

    public SimpleStringProperty photo_urlProperty() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url.set(photo_url);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getTags() {
        return tags.get();
    }

    public SimpleStringProperty tagsProperty() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags.set(tags);
    }

    public String toSearchableStringPackages() {
        return Stream.of(pkgname, pkgstartdate, pkgenddate,
                        pkgdesc, pkgbaseprice, pkgagencycommission)
                .filter(field -> field != null)
                .map(field->field.toString().toLowerCase())
                .collect(Collectors.joining(" "));
    }

}
