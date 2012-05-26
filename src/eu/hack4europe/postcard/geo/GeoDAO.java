package eu.hack4europe.postcard.geo;

import java.util.List;

public class GeoDAO {

    private String status;
    private Results[] results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Results[] getResults() {
        return results;
    }

    public Results getResults(int i) {
        return results[i];
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    public class Results {

        public class Address_components {

            private String long_name;
            private String short_name;

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }

            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }
        }

        private Address_components[] address_components;

        public Address_components[] getAddress_components() {
            return address_components;
        }

        public Address_components getAdress_components(int i) {
            return address_components[i];
        }

        public void setAddress_components(Address_components[] address_components) {
            this.address_components = address_components;
        }

        private String formatted_address;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }
    }

}
