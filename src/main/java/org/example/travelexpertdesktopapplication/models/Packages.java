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

    public Packages(SimpleIntegerProperty packageid, SimpleStringProperty pkgname, SimpleObjectProperty<LocalDate>  pkgstartdate,
                    SimpleObjectProperty<LocalDate>  pkgenddate, SimpleStringProperty pkgdesc, SimpleIntegerProperty pkgbaseprice,
                    SimpleIntegerProperty pkgagencycommission) {
        this.packageid = packageid;
        this.pkgname = pkgname;
        this.pkgstartdate = pkgstartdate;
        this.pkgenddate = pkgenddate;
        this.pkgdesc = pkgdesc;
        this.pkgbaseprice = pkgbaseprice;
        this.pkgagencycommission = pkgagencycommission;
    }


    public int getPackageid() {
        return packageid.get();
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

    public String toSearchableStringPackages() {
        return Stream.of(pkgname, pkgstartdate, pkgenddate,
                        pkgdesc, pkgbaseprice, pkgagencycommission)
                .filter(field -> field != null)
                .map(field->field.toString().toLowerCase())
                .collect(Collectors.joining(" "));
    }

}
