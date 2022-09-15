package cn.dayutec.pigeon.db.ver100;

public class CASESQL100 {
    // 删除车辆通知管理表
    public static final String DROPMOVECAR = "drop table if exists public.move_car";
    // 创建车辆通知管理表
    public static final String CREATEMOVECAR = "create table if not exists public.move_car (" +
            "car_token varchar(50) not null," +
            "user_id varchar(50) not null," +
            "push_token varchar(50) not null," +
            "licence_num varchar(10)," +
            "inform_time bigint not null default 0," +
            "inform_count int default 0 not null," +
            "create_time Timestamp(0) not null," +
            "primary key(car_token)," +
            "UNIQUE (push_token))";
    public static final String MOVECARCOMENT = "COMMENT ON TABLE public.move_car IS '挪车信息表';" +
            "COMMENT ON COLUMN public.move_car.car_token IS '挪车应用的token';" +
            "COMMENT ON COLUMN public.move_car.user_id IS '用户全局唯一id';" +
            "COMMENT ON COLUMN public.move_car.push_token IS '用户推送pushToken';" +
            "COMMENT ON COLUMN public.move_car.licence_num IS '车牌号';" +
            "COMMENT ON COLUMN public.move_car.inform_time IS '上一次通知时间戳';" +
            "COMMENT ON COLUMN public.move_car.inform_count IS '今日通知次数';" +
            "COMMENT ON COLUMN public.move_car.create_time IS '配置创建时间';";
}
