package org.example.travelexpertdesktopapplication.utils;

public class DataFormatter {

    /**
     * A method that takes the inconsistent naming of the destinations from the DB and applies a standardized naming convention to properly display data in the pie chart
     * @param destination, the destination being recieved from the db, before being formatted
     * @return The formatted destination to maintain consistent naming in the pie graph.
     */
    public static String formatDestinationName(String destination) {
        switch (destination) {
            case "Athens, Greece":
                return "Athens";
            case "Cairo, Egypt":
                return "Cairo";
            case "Calcutta, India": // **Consider checking this entry**
                return "Calcutta";
            case "canada": // **This entry should be corrected to "Canada"**
                return "canada"; // **Consider standardizing to "Canada"**
            case "Canada":
                return "Canada";
            case "Cape Town, South Africa":
                return "Cape Town";
            case "Edmonton":
                return "Edmonton";
            case "Grande Prairie":
                return "Grande Prairie";
            case "Greece": // **This entry does not follow city-country format**
                return "Greece"; // **Consider removing or adjusting this entry**
            case "Hong Kong, China":
                return "Hong Kong";
            case "Houston":
                return "Houston";
            case "London, England":
                return "London";
            case "London":
                return "London";
            case "Montreal":
                return "Montreal";
            case "Niagara": // **This entry does not follow city-country format**
                return "Niagara"; // **Consider removing or adjusting this entry**
            case "NZ": // **This entry does not follow the format**
                return "NZ"; // **Consider removing or adjusting this entry**
            case "Paris, France":
                return "Paris";
            case "Peru, argentina, Bollivi": // **This entry does not follow city-country format**
                return "Peru, argentina, Bollivi"; // **Consider fixing or clarifying this entry**
            case "Rio de Janeiro, Brazil":
                return "Rio de Janeiro";
            case "Sydney, Australia":
                return "Sydney";
            case "Sydney":
                return "Sydney";
            case "Toronto":
                return "Toronto";
            case "USA, Mexic": // **This entry should be corrected for consistency**
                return "USA, Mexic"; // **Consider fixing this entry**
            case "Vancouver":
                return "Vancouver";
            case "Victoria":
                return "Victoria";
            case "Winnipeg":
                return "Winnipeg";
            default:
                return destination; // Return original if not found
        }
    }

    }//class