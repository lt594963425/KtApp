package com.example.ktapp;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: MyKrtUtils
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/7/1 8:53
 * @UpdateUser: LiuTao
 */
public class MyKrtUtils {
    private static double N;
    //@将WGS84经纬度转为大地2000坐标。我们是国家电网项目数据很精确的了。
    //@param B 纬度
    //@param L 经度
    //@param degree //
    //@param withBand 默认=false
    //@return
    //@将WGS84经纬度转为大地2000坐标。我们是国家电网项目数据很精确的了。
    //@param B 纬度
    //@param L 经度
    //@param degree //
    //@param withBand 默认=false
    //@return

    /**
     * @param B      纬度
     * @param L      经度
     * @param degree //
     * @return
     */
    public static Tuple gpsGetXY(double B, double L, double degree) {
        double[] xy = {0, 0};

        double a = 6378137;//椭球长半轴
        double b = 6356752.3142451795;//椭球短半轴
        double e = 0.081819190842621;//第一偏心率
        double eC = 0.0820944379496957;//第二偏心率

        double L0 = 0;//中央子午线经度
        int n = 0;//带号
        if (degree == 6) {
            //6度
            n = (int) (Math.round((L + degree / 2) / degree));
            L0 = degree * n - degree / 2;
        } else {
            //3度
            n = (int) Math.round(L / degree);
            L0 = degree * n;
        }

        //开始计算
        double radB = B * Math.PI / 180;//纬度(弧度)
        double radL = L * Math.PI / 180;//经度(弧度)
        double deltaL = (L - L0) * Math.PI / 180;//经度差(弧度)
        double N = a * a / b / Math.sqrt(1 + eC * eC * Math.cos(radB) * Math.cos(radB));
        double C1 = 1.0 + 3.0 / 4 * e * e + 45.0 / 64 * Math.pow(e, 4) + 175.0 / 256 * Math.pow(e, 6) + 11025.0 / 16384 * Math.pow(e, 8);
        double C2 = 3.0 / 4 * e * e + 15.0 / 16 * Math.pow(e, 4) + 525.0 / 512 * Math.pow(e, 6) + 2205.0 / 2048 * Math.pow(e, 8);
        double C3 = 15.0 / 64 * Math.pow(e, 4) + 105.0 / 256 * Math.pow(e, 6) + 2205.0 / 4096 * Math.pow(e, 8);
        double C4 = 35.0 / 512 * Math.pow(e, 6) + 315.0 / 2048 * Math.pow(e, 8);
        double C5 = 315.0 / 131072 * Math.pow(e, 8);
        double t = Math.tan(radB);
        double eta = eC * Math.cos(radB);
        double X = a * (1 - e * e) * (C1 * radB - C2 * Math.sin(2 * radB) / 2 + C3 * Math.sin(4 * radB) / 4 - C4 * Math.sin(6 * radB) / 6 + C5 * Math.sin(8 * radB));

        xy[0] = X + N * Math.sin(radB) * Math.cos(radB) * Math.pow(deltaL, 2) * (1 + Math.pow(deltaL * Math.cos(radB), 2) * (5 - t * t + 9 * eta * eta + 4 * Math.pow(eta, 4)) / 12 + Math.pow(deltaL * Math.cos(radB), 4) * (61 - 58 * t * t + Math.pow(t, 4)) / 360) / 2;
        xy[1] = N * deltaL * Math.cos(radB) * (1 + Math.pow(deltaL * Math.cos(radB), 2) * (1 - t * t + eta * eta) / 6 + Math.pow(deltaL * Math.cos(radB), 4) * (5 - 18 * t * t + Math.pow(t, 4) - 14 * eta * eta - 58 * eta * eta * t * t) / 120) + 500000;// +n * 1000000;

        return new Tuple(xy[0], xy[1]);
    }


    //@将大地2000转为WGS84
    //高斯投影反算为大地平面。
    // x，y ，高斯平面坐标点
    //L0 通过经纬度来获取中央带所在带的角度
    //return B纬度 , L经度
    public static LatLng xyTowgs84(double x, double y, double L0) {
        //中央子午线经度
        //WGS-84   椭球体参数
        double a = 6378137.0; //major semi axis
        double efang = 0.0066943799901413; //square of e
        double e2fang = 0.0067394967422764; //suqre of e2

        y = y - 500000;

        //主曲率计算
        double m0, m2, m4, m6, m8;
        m0 = a * (1 - efang);
        m2 = 3.0 / 2.0 * efang * m0;
        m4 = efang * m2 * 5.0 / 4.0;
        m6 = efang * m4 * 7.0 / 6.0;
        m8 = efang * m6 * 9.0 / 8.0;

        //子午线曲率计算
        double a0, a2, a4, a6, a8;
        a0 = m0 + m2 / 2.0 + m4 * 3.0 / 8.0 + m6 * 5.0 / 16.0 + m8 * 35.0 / 128.0;
        a2 = m2 / 2.0 + m4 / 2.0 + m6 * 15.0 / 32.0 + m8 * 7.0 / 16.0;
        a4 = m4 / 8.0 + m6 * 3.0 / 16.0 + m8 * 7.0 / 32.0;
        a6 = m6 / 32.0 + m8 / 16.0;
        a8 = m8 / 128.0;

        double X = x;
        double FBf = 0;
        double Bf0 = X / a0, Bf1 = 0;

        //计算Bf的值，直到满足条件
        while ((Bf0 - Bf1) >= 0.0001) {
            Bf1 = Bf0;
            FBf = -a2 * Math.sin(2 * Bf0) / 2 + a4 * Math.sin(4 * Bf0) / 4 - a6 * Math.sin(6 * Bf0) / 6 + a8 * Math.sin(8 * Bf0) / 8;
            Bf0 = (X - FBf) / a0;
        }
        double Bf = Bf0;
        //计算公式中参数
        double Wf = Math.sqrt(1 - efang * Math.sin(Bf) * Math.sin(Bf));
        double Nf = a / Wf;
        double Mf = a * (1 - efang) / Math.pow(Wf, 3);
        double nffang = e2fang * Math.cos(Bf) * Math.cos(Bf);
        double tf = Math.tan(Bf);
        double B = Bf - tf * y * y / (2 * Mf * Nf) + tf * (5 + 3 * tf * tf + nffang - 9 * nffang * tf * tf) * Math.pow(y, 4) / (24 * Mf * Math.pow(Nf, 3)) - tf * (61 + 90 * tf * tf + 45 * Math.pow(tf, 4)) * Math.pow(y, 6) / (720 * Mf * Math.pow(Nf, 5));
        double l = y / (Nf * Math.cos(Bf)) - (1 + 2 * tf * tf + nffang) * Math.pow(y, 3) / (6 * Math.pow(Nf, 3) * Math.cos(Bf)) + (5 + 28 * tf * tf + 24 * Math.pow(tf, 4)) * Math.pow(y, 5) / (120 * Math.pow(Nf, 5) * Math.cos(Bf));
        double L = l + L0;
        //转化成为十进制经纬度格式
        double[] array_B = rad2dms(B);
        double[] array_L = rad2dms(L);
        double Bdec = dms2dec(array_B);
        double Ldec = dms2dec(array_L);

        return new LatLng(Bdec, Ldec);
    }

    private static double p = 180.0 / Math.PI * 3600;

    //通过经纬度来获取中央带所在带的角度
    //@param B 纬度
    //@param L 经度
    //@param N 带[3,6带度]
    private static double gaussLongToDegreen(double B, double L, int N) {
        //计算该地区的中央子午线经度
        double L00 = Math.round((L / N)) * N;
        double degreen = L00 / 180 * 3.1415926;
        return degreen;
    }

    //将弧度,,转化为度分秒
    private static double[] rad2dms(double rad) {
        double[] a = {0, 0, 0};
        double dms = rad * p;
        a[0] = Math.floor(dms / 3600.0);
        a[1] = Math.floor((dms - a[0] * 3600) / 60.0);
        a[2] = ((int) Double.parseDouble((dms - a[0] * 3600) + "") - a[1] * 60);
        return a;
    }

    //将度分秒转化为十进制坐标
    private static double dms2dec(double[] dms) {
        double dec = 0.0;
        dec = dms[0] + dms[1] / 60.0 + dms[2] / 3600.0;
        return dec;
    }


    /**
     * WGS84坐标系转大地坐标系
     *
     * @param latitude
     * @param longitude
     * @param altitude
     * @return
     */
    public static double[] get_coordinate(double latitude, double longitude, double altitude) {

        double B = Math.toRadians(latitude), L = Math.toRadians(longitude), H = altitude, x, y, z;
        double f = 1 / 298.257223563, r = 6378137;
        double b = r * (1 - f), e = Math.sqrt(2 * f - f * f);
        N = r / Math.sqrt(1 - e * e * Math.sin(B) * Math.sin(B));
        x = (N + H) * Math.cos(B) * Math.cos(L);
        y = (N + H) * Math.cos(B) * Math.sin(L);
        z = (N * (1 - e * e) + H) * Math.sin(B);
        double[] data = {x, y, z};
        return data;
    }

    /**
     * 大地坐标系系转WGS84坐标
     *
     * @param m 经度
     * @param n 纬度
     * @param q
     * @return
     */

    public static double[] returnlocation(double m, double n, double q) {
        double a = 6378137;//椭球长半轴
        double f = 1 / 298.257223563;
        double b = 6356752.3142451795;//椭球短半轴
        double e = 0.081819190842621;//第一偏心率
        double eC = 0.0820944379496957;//第二偏心率

        double efang = 0.0066943799901413; //square of e
        double e2fang = 0.0067394967422764; //suqre of e2
        double Bf = 000;
        double Wf = Math.sqrt(1 - e * e * Math.sin(Bf) * Math.sin(Bf));
        double N = a / Wf;//W=√(1-e²sin²B) B为纬度

        double L = Math.atan(n / m) + Math.PI;
        double B = Math.atan(Math.cos(L) * q / m / (1 - N * e * e / (N + 40.0038)));
        double H = (m / Math.cos(L) / Math.cos(B) - N + n / Math.sin(L) / Math.cos(B) - N) / 2;
        double L1 = L / Math.PI * 180, B1 = B / Math.PI * 180;
        double[] data = {B1, L1, H};
        return data;
    }


    /****************************************************************************************/

    double targetZ;
    double targetB;
    double targetL;
    double Axis_WGS84_a =6378137;
    double Axis_WGS84_b =6356752.314;

    //第一步转换，大地坐标系换换成空间直角坐标系
    private void BLHtoXYZ(double B, double L, double H, double aAxis, double bAxis) {
        double dblD2R = Math.PI / 180;
        double e1 = Math.sqrt(Math.pow(aAxis, 2) - Math.pow(bAxis, 2)) / aAxis;

        double N = aAxis / Math.sqrt(1.0 - Math.pow(e1, 2) * Math.pow(Math.sin(B * dblD2R), 2));
        double targetX = (N + H) * Math.cos(B * dblD2R) * Math.cos(L * dblD2R);
        double targetY = (N + H) * Math.cos(B * dblD2R) * Math.sin(L * dblD2R);
        double targetZ = (N * (1.0 - Math.pow(e1, 2)) + H) * Math.sin(B * dblD2R);
        //第二步转换，空间直角坐标系里七参数转换
        double Ex = TransParaSeven.m_dbXRotate / 180 * Math.PI;
        double Ey = TransParaSeven.m_dbYRotate / 180 * Math.PI;
        double Ez = TransParaSeven.m_dbZRotate / 180 * Math.PI;

        double newX = TransParaSeven.m_dbXOffset + TransParaSeven.m_dbScale * targetX + targetY * Ez - targetZ * Ey + targetX;
        double newY = TransParaSeven.m_dbYOffset + TransParaSeven.m_dbScale * targetY - targetX * Ez + targetZ * Ex + targetY;
        double newZ = TransParaSeven.m_dbZOffset + TransParaSeven.m_dbScale * targetZ + targetX * Ey - targetY * Ex + targetZ;
    }

    //第三步转换，空间直接坐标系转换为大地坐标系
    private void XYZtoBLH(double X, double Y, double Z, double aAxis, double bAxis) {
        double e1 = (Math.pow(aAxis, 2) - Math.pow(bAxis, 2)) / Math.pow(aAxis, 2);
        double e2 = (Math.pow(aAxis, 2) - Math.pow(bAxis, 2)) / Math.pow(bAxis, 2);

        double S = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
        double cosL = X / S;
        double B = 0;
        double L = 0;

        L = Math.acos(cosL);
        L = Math.abs(L);

        double tanB = Z / S;
        B = Math.atan(tanB);
        double c = aAxis * aAxis / bAxis;
        double preB0 = 0.0;
        double ll = 0.0;
        double N = 0.0;
        //迭代计算纬度
        do {
            preB0 = B;
            ll = Math.pow(Math.cos(B), 2) * e2;
            N = c / Math.sqrt(1 + ll);

            tanB = (Z + N * e1 * Math.sin(B)) / S;
            B = Math.atan(tanB);
        }
        while (Math.abs(preB0 - B) >= 0.0000000001);

        ll = Math.pow(Math.cos(B), 2) * e2;
        N = c / Math.sqrt(1 + ll);

        targetZ = Z / Math.sin(B) - N * (1 - e1);
        targetB = B * 180 / Math.PI;
        targetL = L * 180 / Math.PI;
    }

    //第四部转换，高斯变换，大地坐标系转平面坐标系，84转80
    private void gaussBLtoXY(double mX, double mY) {
        double m_aAxis = Axis_WGS84_a; //参考椭球长半轴
        double m_bAxis = Axis_WGS84_b; //参考椭球短半轴
        double m_dbMidLongitude = TransParaSeven.daihao * 3;//中央子午线经度 济南117 威海123 巴州 87
        double m_xOffset = 500000;
        double m_yOffset = 0.0;
        try {
            //角度到弧度的系数
            double dblD2R = Math.PI / 180;
            //代表e的平方
            double e1 = (Math.pow(m_aAxis, 2) - Math.pow(m_bAxis, 2)) / Math.pow(m_aAxis, 2);
            //代表e'的平方
            double e2 = (Math.pow(m_aAxis, 2) - Math.pow(m_bAxis, 2)) / Math.pow(m_bAxis, 2);
            //a0
            double a0 = m_aAxis * (1 - e1) * (1.0 + (3.0 / 4.0) * e1 + (45.0 / 64.0) * Math.pow(e1, 2) + (175.0 / 256.0) * Math.pow(e1, 3) + (11025.0 / 16384.0) * Math.pow(e1, 4));
            //a2
            double a2 = -0.5 * m_aAxis * (1 - e1) * (3.0 / 4 * e1 + 60.0 / 64 * Math.pow(e1, 2) + 525.0 / 512.0 * Math.pow(e1, 3) + 17640.0 / 16384.0 * Math.pow(e1, 4));
            //a4
            double a4 = 0.25 * m_aAxis * (1 - e1) * (15.0 / 64 * Math.pow(e1, 2) + 210.0 / 512.0 * Math.pow(e1, 3) + 8820.0 / 16384.0 * Math.pow(e1, 4));
            //a6
            double a6 = (-1.0 / 6.0) * m_aAxis * (1 - e1) * (35.0 / 512.0 * Math.pow(e1, 3) + 2520.0 / 16384.0 * Math.pow(e1, 4));
            //a8
            double a8 = 0.125 * m_aAxis * (1 - e1) * (315.0 / 16384.0 * Math.pow(e1, 4));
            ////纬度转换为弧度表示
            //B
            double B = mX * dblD2R;
            //l
            double l = (mY - m_dbMidLongitude) * dblD2R;
            ////X
            double X = a0 * B + a2 * Math.sin(2.0 * B) + a4 * Math.sin(4.0 * B) + a6 * Math.sin(6.0 * B) + a8 * Math.sin(8.0 * B);
            //
            double ll = Math.pow(Math.cos(B), 2) * e2;
            double c = m_aAxis * m_aAxis / m_bAxis;
            //N
            double N = c / Math.sqrt(1 + ll);
            //t
            double t = Math.tan(B);
            double p = Math.cos(B) * l;
            double dby = X + N * t * (1 + ((5.0 - t * t + (9.0 + 4.0 * ll) * ll) + ((61.0 + (t * t - 58.0) * t * t + (9.0 - 11.0 * t * t) * 30.0 * ll) + (1385.0 + (-31111.0 + (543 - t * t) * t * t) * t * t) * p * p / 56.0) * p * p / 30.0) * p * p / 12.0) * p * p / 2.0;
            double dbx;
            dbx = N * (1.0 + ((1.0 - t * t + ll) + ((5.0 + t * t * (t * t - 18.0 - 58.0 * ll) + 14 * ll) + (61.0 + (-479.0 + (179.0 - t * t) * t * t) * t * t) * p * p / 42.0) * p * p / 20.0) * p * p / 6.0) * p;
            double   mTargetX = dbx + m_xOffset;
            double   mTargetY = dby + m_yOffset;
        } catch (Exception ex) {
        }
    }
}

