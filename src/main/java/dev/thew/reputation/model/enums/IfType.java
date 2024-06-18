package dev.thew.reputation.model.enums;

public enum IfType {

    USERHAVEMORE{

        @Override
        public boolean check(int need, int has) {
            return need > has;
        }
    },
    USERHAVELESS{

        @Override
        public boolean check(int need, int has) {
            return need < has;
        }
    };

    public abstract boolean check(int need, int has);

}
