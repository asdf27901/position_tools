

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by Don on 2018/8/14.
 * GNSS数据基本操作类
 */

public class GnssUtil {

    public static final String TAG = "GnssUtil";
    public final static double _1E3 = 1E3;
    public final static double _1E6 = 1E6;
    public final static double _1E9 = 1E9;
    public final static double NUMBER_SECONDS_PER_WEEK = 604800;
    public final static double NUMBER_NANO_SECONDS_PER_WEEK = 604800E9;
    public final static long NUMBER_SECOND_3HOUR = 10800;
    public final static int NUMBER_LEAPSECOND = 18;
    public final static long GPS_UTC_EPOCH_OFFSET_NANOS = 315964800000000000l;
    public final static double SPEED_OF_LIGHT = 299792458.0;

    public final static long ReceivedSvTimeNanosFilter = 100000000000L;
    public final static int ReceivedSvTimeUncertaintyNanosFilter = 1000;

    /**
     * This Galileo measurement's tracking state has E1B/C code lock.
     */
    public static final int STATE_GAL_E1BC_CODE_LOCK = (1 << 10);
    /**
     * This Galileo measurement's tracking state has E1C secondary code lock.
     */
    public static final int STATE_GAL_E1C_2ND_CODE_LOCK = (1 << 11);
    /**
     * This Galileo measurement's tracking state has E1B page sync.
     */
    public static final int STATE_GAL_E1B_PAGE_SYNC = (1 << 12);

    /**
     * 光速，m/s
     */
    public static final double C = 299792458.0;
    /**
     * 光速，m/ms
     */
    public static final double C_MS = C / 1000;

    /**
     * 光速，m/s，大数类型
     */
    public static final BigDecimal C_BD = BigDecimal.valueOf(C);
    /**
     * 光速，m/ms，大数类型
     */
    public static final BigDecimal C_MS_BD = BigDecimal.valueOf(C_MS);

    /**
     * GLO闰秒
     */
    public static final int GLO_LEAPSECOND = 18;

    /**
     * GLO闰秒，大数类型
     */
    public static final BigDecimal GLO_LEAPSECOND_BD = BigDecimal.valueOf(18);
    /**
     * 周内秒
     */
    public static final BigDecimal WEEKSEC_BD = BigDecimal.valueOf(604800);
    /**
     * 周内纳秒
     */
    public static final BigDecimal _604800E9_BD = BigDecimal.valueOf(604800E9);

    /**
     * 半周内纳秒
     */
    public static final BigDecimal _302400E9_BD = BigDecimal.valueOf(302400E9);

    /**
     * 天纳秒
     */
    public static final BigDecimal _86400E9_BD = BigDecimal.valueOf(86400E9);
    /**
     * 三小时秒数
     */
    public static final BigDecimal _10800_BD = BigDecimal.valueOf(10800);
    /**
     * 100毫秒纳秒数
     */
    public static final BigDecimal _100MILLI_NANOS_BD = BigDecimal.valueOf(100E6);
    /**
     * 1E6 的大数类型
     */
    public static final BigDecimal _1E6_BD = BigDecimal.valueOf(1E6);
    /**
     * 1E9 的大数类型
     */
    public static final BigDecimal _1E9_BD = BigDecimal.valueOf(1E9);
    /**
     * 1E14 的大数类型
     */
    public static final BigDecimal _14E9_BD = BigDecimal.valueOf(14E9);

    /**
     * 2的19次方 的大数类型
     */
    public static final BigDecimal _2POW19_BD = BigDecimal.valueOf(Math.pow(2, 19));

    /**
     * 1024 的大数类型
     */
    public static final BigDecimal _1024_BD = BigDecimal.valueOf(1024);

    /**
     * 2的24次方 的大数类型
     */
    public static final BigDecimal _2POW24_BD = BigDecimal.valueOf(Math.pow(2, 24));

    /**
     * 1000 的大数类型
     */
    public static final BigDecimal _1000_BD = BigDecimal.valueOf(1000);

    /**
     * 14000 的大数类型
     */
    public static final BigDecimal _14000_BD = BigDecimal.valueOf(14000);

    /**
     * 2的29次方 的大数类型
     */
    public static final BigDecimal _2POW29_BD = BigDecimal.valueOf(Math.pow(2, 29));

    /**
     * 2的31次方 的大数类型
     */
    public static final BigDecimal _2POW31_BD = BigDecimal.valueOf(Math.pow(2, 31));

    /**
     * 2的10次方 的大数类型
     */
    public static final BigDecimal _2POW10_BD = BigDecimal.valueOf(Math.pow(2, 10));

    /**
     * 2的14次方 的大数类型
     */
    public static final BigDecimal _2POW14_BD = BigDecimal.valueOf(Math.pow(2, 14));

    /**
     * -1 的大数类型
     */
    public static final BigDecimal _OPPOSITE_BD = BigDecimal.valueOf(-1);


    /**
     * 各星座频段值
     */
    public static final double FREQ1 = 1.57542E9;        /* L1/E1  frequency (Hz) */
    public static final double FREQ2 = 1.22760E9;      /* L2     frequency (Hz) */
    public static final double FREQ5 = 1.17645E9;      /* L5/E5a frequency (Hz) */
    public static final double FREQ6 = 1.27875E9;      /* E6/LEX frequency (Hz) */
    public static final double FREQ7 = 1.20714E9;       /* E5b    frequency (Hz) */
    public static final double FREQ8 = 1.191795E9;      /* E5a+b  frequency (Hz) */
    public static final double FREQ9 = 2.492028E9;       /* S      frequency (Hz) */
    public static final double FREQ1_GLO = 1.60200E9;       /* GLONASS G1 base frequency (Hz) */
    public static final double DFRQ1_GLO = 0.56250E6;       /* GLONASS G1 bias frequency (Hz/n) */
    public static final double FREQ2_GLO = 1.24600E9;      /* GLONASS G2 base frequency (Hz) */
    public static final double DFRQ2_GLO = 0.43750E6;       /* GLONASS G2 bias frequency (Hz/n) */
    public static final double FREQ3_GLO = 1.202025E9;       /* GLONASS G3 frequency (Hz) */
    public static final double FREQ1_CMP = 1.561098E9;      /* BeiDou B1 frequency (Hz) */
    public static final double FREQ2_CMP = 1.20714E9;      /* BeiDou B2 frequency (Hz) */
    public static final double FREQ3_CMP = 1.26852E9;      /* BeiDou B3 frequency (Hz) */


    /**
     * GGA类型判断
     *
     * @param s
     * @return
     */
    private static boolean isGgaHead(String s) {
        switch (s) {
            case "$GPGGA":
            case "$GNGGA":
            case "$GBGGA":
            case "$GLGGA":
            case "$BDGGA":
                return true;
            default:
                return false;
        }
    }

    /**
     * RMC类型判断
     *
     * @param s
     * @return
     */
    private static boolean isRmcHead(String s) {
        switch (s) {
            case "$GPRMC":
            case "$GNRMC":
                return true;
            default:
                return false;
        }
    }








    /**
     * 通过卫星类型ID获取卫星系统名称
     *
     * @param constellationType
     * @return
     */
    public static String getNameByConstellationType(int constellationType) {
        switch (constellationType) {
            case 1:
                return "GPS";
            case 2:
                return "SBAS";
            case 3:
                return "GLONASS";
            case 4:
                return "QZSS";
            case 5:
                return "BEIDOU";
            case 6:
                return "GALILEO";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * 通过星座类型获取一个星座简称
     */
    public static String getShortNameByConstellationType(int constellationType) {
        switch (constellationType) {
            case 1:
                return "G";
            case 2:
                return "S";
            case 3:
                return "R";
            case 4:
                return "J";
            case 5:
                return "C";
            case 6:
                return "E";
            default:
                return "UNKNOWN";
        }
    }


    /**
     * 转化GNSS数据中坐标为普通度分秒格式，并保留小数
     *
     * @param loc   GNSS格式中坐标如2346.12121121,11312.12112121
     * @param scale 保留小数位
     * @return
     */
    public static double convertGgaLocation(double loc, int scale) {
        if (scale < 0) {
            scale = 9;
        }
        double result = (int) (loc / 100.0) + (loc - ((int) (loc / 100)) * 100) / 60.0;
        return new BigDecimal(result).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转化GNSS数据中坐标为普通度分秒格式
     *
     * @param lat
     * @param lng
     * @return
     */
    public static double[] convertGgaLocation(double lat, double lng) {
        double sLat = (int) (lat / 100.0) + (lat - ((int) (lat / 100)) * 100) / 60.0;
        double slng = (int) (lng / 100.0) + (lng - ((int) (lng / 100)) * 100) / 60.0;
        return new double[]{new BigDecimal(sLat).setScale(9, BigDecimal.ROUND_HALF_UP).doubleValue(), new BigDecimal(slng).setScale(9, BigDecimal.ROUND_HALF_UP).doubleValue()};
    }



    public static StringBuilder convert2GgaLocation(double data) {
        StringBuilder sb = new StringBuilder();
        String latStr = String.valueOf(data);
        String[] strs = latStr.split("\\.");
        String latInteger = strs[0];
        String latDecimal = strs[1];
        sb.append(latInteger);
        double dd = Double.valueOf("0." + latDecimal) * 60.0;
        String ddStr = String.valueOf(dd);
        String[] strsdd = ddStr.split("\\.");
        String ddInteger = strsdd[0];
        if (ddInteger.length() == 1)
            sb.append("0");
        sb.append(dd);
        return sb;
    }





    /**
     * Creates a unique key to identify this satellite using a combination of both the svid and
     * constellation type
     *
     * @return a unique key to identify this satellite using a combination of both the svid and
     * constellation type
     */
    public static String createGnssSatelliteKey(int svid, int constellationType) {
        return String.valueOf(svid) + " " + String.valueOf(constellationType);
    }



    /**
     * 计算传输时间
     *
     * @return
     */
    public static BigDecimal evalD(BigDecimal tRxBd, BigDecimal tTxBd) {
        BigDecimal dBd = (tRxBd.subtract(tTxBd)).divide(_1E6_BD, 32, BigDecimal.ROUND_HALF_DOWN);
        return dBd;
    }


    /**
     * 将RTCM中时间转为当天毫秒数，对应GGA中时间戳对应的毫秒数
     * 与{@link GnssUtil#getGgaTimeWeeksec(String)}结果用于对比
     *
     * @param gnsstime
     * @return
     */
    public static long getRtcmTimeWeeksec(long gnsstime) {
        return gnsstime % 86400000 - 18 * 1000;
    }

    /**
     * 将GGA中时间转为毫秒数
     *
     * @param gga #eg : $GNGGA,070226.00,2307.34002,N,11320.72812,E,1,14,1.65,42.8,M,,M,,*68
     * @return
     */
    public static long getGgaTimeWeeksec(String gga) {
        String[] datas = gga.split(",");
        long sec = 0;
        try {
            sec += Integer.valueOf(datas[1].substring(0, 2)) * 3600 * 1000;
            sec += Integer.valueOf(datas[1].substring(2, 4)) * 60 * 1000;
            sec += Integer.valueOf(datas[1].substring(4, 6)) * 1000;
            sec += Integer.valueOf(datas[1].split("\\.")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sec;
    }

    public final static int mday[] = { /* # of days in a month */
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31,
            31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public static void time2epoch() {

    }

    public static void epoch2time() {

    }

}
