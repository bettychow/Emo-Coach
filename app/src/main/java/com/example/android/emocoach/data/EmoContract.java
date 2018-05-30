package com.example.android.emocoach.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class EmoContract {
    private EmoContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.android.emocoach";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EMOS = "emos";

    public static final class EmoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EMOS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EMOS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EMOS;

        /** Name of database table for emos */
        public final static String TABLE_NAME = "emos";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_EMO_TYPE ="emo_type";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_EMO_DATE_TIME= "date_time";


        /**
         * Possible values for the type of emo.
         */
        public static final int EMO_HAPPY_ = 0;
        public static final int EMO_JOYFUL = 1;
        public static final int EMO_SATISFIED = 2;
        public static final int EMO_ENERGETIC = 3;
        public static final int EMO_PEACEFUL_ = 4;
        public static final int EMO_GRATEFUL = 5;
        public static final int EMO_UPSET = 6;
        public static final int EMO_ANGRY = 7;
        public static final int EMO_ANXIOUS = 8;
        public static final int EMO_SAD = 9;
        public static final int EMO_TIRED = 10;
        public static final int EMO_STRESSED = 11;




//        public static boolean isValidGender (int gender) {
//            if(gender == GENDER_UNKNOWN || gender == GENDER_FEMALE || gender == GENDER_MALE) {
//                return true;
//            }
//            return false;
//        }

    }


}


