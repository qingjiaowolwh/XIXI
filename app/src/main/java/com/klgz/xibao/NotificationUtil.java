//package com.klgz.xibao;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.v4.app.NotificationCompat;
//
//import com.megvii.polizeilichassistant.conts.Constant;
//import com.megvii.polizeilichassistant.receivers.NotificationReceiver;
//
///**
// * Created by lwh on 2017/9/27.
// */
//
//public class NotificationUtil {
//
//    public static void showNotification(Context mContext, int drawableID, String title, String msgContent) {
//        Intent broadcastIntent = new Intent(mContext, NotificationReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.
//            getBroadcast(mContext, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext)
//            .setAutoCancel(true)
//            .setContentTitle(title)
//            .setTicker(msgContent)
//            .setContentText(msgContent)
//            .setContentIntent(pendingIntent)
//            .setSmallIcon(drawableID);
//        //     .setWhen(System.currentTimeMillis());
//        if (SharedPreferencesUtil.getInstance(mContext).getBoolean(Constant.IS_VIBRATE,true)) {
//            notification.setDefaults(Notification.DEFAULT_VIBRATE);
//        }
//        if (SharedPreferencesUtil.getInstance(mContext).getBoolean(Constant.IS_BELL,true)){
//            String str_uri = String.format("android.resource://%s/%s", mContext.getPackageName(), R.raw.notification_bell);
//            notification.setSound(Uri.parse(str_uri));
//        }
//
//        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        // 启动提醒
//        notificationManager.notify(2, notification.build());
//    }
//
////    public static void showJumpNotification(Context mContext, int drawableID, String title, String msgContent) {
////        Intent intent = new Intent(mContext, LoginActivity.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        PendingIntent notifyIntent =
////                PendingIntent.getActivity(
////                        mContext,
////                        0,
////                        intent,
////                        PendingIntent.FLAG_UPDATE_CURRENT
////                );
////        NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext)
////                .setAutoCancel(true)
////                .setContentTitle("标题")
////                .setContentText("内容")
////                .setContentIntent(notifyIntent)
////                .setDefaults(Notification.DEFAULT_VIBRATE)
////                .setSmallIcon(R.mipmap.ic_launcher);
////        if (SharedPreferencesUtil.getInstance(mContext).getBoolean(Constant.IS_BELL, true)) {
////            String str_uri = String.format("android.resource://%s/%s", mContext.getPackageName(), R.raw.notification_bell);
////            notification.setSound(Uri.parse(str_uri));
////        }
////        NotificationManager mNotificationManager =
////                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
////        mNotificationManager.notify(001, notification.build());
////    }
//
////    public static void showBigView(Context mContext) {
////        Intent resultIntent = new Intent(mContext, LoginActivity.class);
////        resultIntent.putExtra("message", "message");
////        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
////                Intent.FLAG_ACTIVITY_CLEAR_TASK);
////
////        PendingIntent resultPendingIntent =
////                PendingIntent.getActivity(
////                        mContext,
////                        0,
////                        resultIntent,
////                        PendingIntent.FLAG_UPDATE_CURRENT
////                );
////
////
////        Intent dismissIntent = new Intent(mContext, LoginActivity.class);
////        dismissIntent.setAction("ACTION_DISMISS");
////        PendingIntent piDismiss = PendingIntent.getService(mContext, 0, dismissIntent, 0);
////
////        Intent snoozeIntent = new Intent(mContext, LoginActivity.class);
////        snoozeIntent.setAction("ACTION_SNOOZE");
////        PendingIntent piSnooze = PendingIntent.getService(mContext, 0, snoozeIntent, 0);
////
////
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
////                .setSmallIcon(R.mipmap.icon_app)
////                .setContentTitle("Title")
////                .setContentText("contentText")
////                .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
////                .setStyle(new NotificationCompat.BigTextStyle().bigText("bigText"))
////                .addAction(R.mipmap.ic_launcher,
////                        getResources().getString(R.string.cancle), piDismiss)
////                .addAction(R.mipmap.ic_launcher,
////                        "snooze", piSnooze);
////        builder.setContentIntent(resultPendingIntent);
////
////        NotificationManager mNotificationManager =
////                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
////        mNotificationManager.notify(002, builder.build());
////    }
//}
