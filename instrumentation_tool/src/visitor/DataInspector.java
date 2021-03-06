package visitor;

import util.EpsilonCheck;
import util.Constants;
import util.EnsureEncoding;
import util.Logging;

/**
 * Visitor class - Implementation for all the analysis run on the Output Stream to inspect the mock-up data.
 *
 * @author Anshul Vyas
 */
public class DataInspector implements Visitor {

    private static DataInspector dataInspector = null;

    /**
     *  Instance of the Logging class
     */
    Logging logger = Logging.getInstance();

    private DataInspector() { }

    /**
     * @return new instance of the DataInspector
     */
    public static DataInspector getInstance() {
        if (dataInspector == null) {
            dataInspector = new DataInspector();
        }

        return dataInspector;
    }

    /**
     * Overrides the visit() method to run the analysis for Geolocation coordinates
     *
     * @param locationObserver
     */
    @Override
    public void visit(LocationObserver locationObserver) {
        byte[] dumpBytes = locationObserver.getLocationBuffer();

        if (dumpBytes != null) {
            String coordinatesDecoded = EnsureEncoding.decode(dumpBytes);          // detect encoding
            try {
                Double coordinates = Double.valueOf(coordinatesDecoded);           // check if the characters can be converted to Double or not
                if (EpsilonCheck.epsilonDoubleEqual(coordinates, Constants.ASPECT_LATITUDE)) {
                    logger.printLog("\n\nERROR 1.0 :- Privacy Violation. Latitude coordinates detected in the HTTP Stream.");
                }
                if (EpsilonCheck.epsilonDoubleEqual(coordinates, Constants.ASPECT_LONGITUDE)) {
                    logger.printLog("\n\nERROR 1.1 :- Privacy Violation. Longitude coordinates detected in the HTTP Stream.");
                }
            } catch (NumberFormatException e) {

            }
        }
    }

    /**
     * Overrides the visit() method to run the analysis for IMEI number
     * @param imeiObserver
     */
    @Override
    public void visit(IMEIObserver imeiObserver) {
        byte[] dumpBytes = imeiObserver.getIMEIBuffer();

        if(dumpBytes != null) {
            String imeiDecoded = EnsureEncoding.decode(dumpBytes);     // Detect Encoding
            if (imeiDecoded.equals(Constants.ASPECT_IMEI)) {
                logger.printLog("\n\nERROR 2 :- Possible Privacy Violation. " +
                                           "IMEI number detected in HTTP Stream.");
            }
        }
    }

    /**
     * Overrides the visit() method to run the analysis for contact information
     *
     * @param contactObserver
     */
    @Override
    public void visit(ContactObserver contactObserver) {
        byte[] dumpBytes = contactObserver.getContactBuffer();

        if(dumpBytes != null) {
            String contactInformation = EnsureEncoding.decode(dumpBytes);           // detect encoding
            if (contactInformation.equals(Constants.ASPECT_CONTACT_FIRST_NAME)) {
                logger.printLog("\n\nERROR 3.0 :- Privacy Violation. Contact Name detected in HTTP Stream.");
            } else if (contactInformation.equals(Constants.ASPECT_CONTACT_LAST_NAME)) {
                logger.printLog("\n\nERROR 3.0 :- Privacy Violation. Contact Last Name detected in HTTP Stream.");
            } else if (contactInformation.equals(Constants.ASPECT_CONTACT_NUMBER)) {
                logger.printLog("\n\nERROR 3.1 :- Privacy Violation. Contact phone number detected in HTTP Stream.");
            } else if (contactInformation.equals(Constants.ASPECT_CONTACT_EMAIL)) {
                logger.printLog("\n\nERROR 3.2 :- Privacy Violation. Contact email detected in HTTP Stream.");
            }
        }
    }
}
