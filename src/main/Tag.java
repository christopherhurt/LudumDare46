package main;

enum Tag {

    JAGUAR("Jaguar");

    private final String mTag;

    Tag(String pTag) {
        mTag = pTag;
    }

    String getTag() {
        return mTag;
    }

}
