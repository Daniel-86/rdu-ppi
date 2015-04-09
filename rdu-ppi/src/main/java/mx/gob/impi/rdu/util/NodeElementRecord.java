package mx.gob.impi.rdu.util;

public class NodeElementRecord {
    private String nodename;
    private String nodedescription;
    private String nodetext;

    /**
     * Getter para nodename
     *
     * @return String
     */
    public String getNodename() {
        return nodename;
    }

    /**
     * Getter para nodedescription
     *
     * @return String
     */
    public String getNodedescription() {
        return nodedescription;
    }

    /**
     * Getter para nodetext
     *
     * @return String
     */
    public String getNodetext() {
        return nodetext;
    }

    /**
     * Setter para nodename
     *
     * @param nodename the nodename to set
     */
    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    /**
     * Setter para nodedescription
     *
     * @param nodedescription the nodedescription to set
     */
    public void setNodedescription(String nodedescription) {
        this.nodedescription = nodedescription;
    }

    /**
     * Setter para nodetext
     *
     * @param nodetext the nodetext to set
     */
    public void setNodetext(String nodetext) {
        this.nodetext = nodetext;
    }
}