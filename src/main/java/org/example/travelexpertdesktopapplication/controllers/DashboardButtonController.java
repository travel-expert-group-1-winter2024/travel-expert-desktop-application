package org.example.travelexpertdesktopapplication.controllers;

public class DashboardButtonController {
    protected DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void updateDashBoardButtons(int code) {
        switch (code){
            case 1:
                displayPackagesButtons();
                break;
            case 2:
                displayCustomerButtons();
                break;
            case 3:
                displaySuppliersButtons();
                break;
            case 4:
                displayProductButtons();
                break;
            case 5:
                displayReportButtons();
                break;
            case 6:
                displayProfileButtons();
                break;
            case 7:
                displayManagerButtons();
                break;
            default:
                System.out.println("Unknown code called");
                break;
        }
    }

    private void displayManagerButtons() {
        System.out.println("Code 7 called");
    }

    private void displayProfileButtons() {
        System.out.println("Code 6 called");
    }

    private void displayReportButtons() {
        System.out.println("Code 5 called");
    }

    private void displayProductButtons() {
        System.out.println("Code 4 called");
    }

    private void displaySuppliersButtons() {
        System.out.println("Code 3 called");
    }

    private void displayCustomerButtons() {
        System.out.println("Code 2 called");
    }

    private void displayPackagesButtons() {
        System.out.println("Code 1 Called");
    }
}
