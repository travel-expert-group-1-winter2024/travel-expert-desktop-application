package org.example.travelexpertdesktopapplication.controllers;

public class CentralController {
    //* References to the other controllers
    private DashboardController dashboardController;
    private AgentDrawerController agentDrawerController;
    private ManagerDrawerController managerDrawerController;

    //* Setters
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setAgentDrawerController(AgentDrawerController agentDrawerController) {
        this.agentDrawerController = agentDrawerController;
    }

    public void setManagerDrawerController(ManagerDrawerController managerDrawerController) {
        this.managerDrawerController = managerDrawerController;
    }


}
