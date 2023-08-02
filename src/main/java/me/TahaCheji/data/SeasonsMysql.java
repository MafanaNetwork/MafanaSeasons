package me.TahaCheji.data;

import me.TahaCheji.MafanaSeasons;
import me.TahaCheji.mysqlData.MySQL;
import me.TahaCheji.mysqlData.MysqlValue;
import me.TahaCheji.mysqlData.SQLGetter;

import java.util.UUID;

public class SeasonsMysql extends MySQL{
    public SeasonsMysql() {
        super("localhost", "3306", "mafanation", "root", "");
    }

    private UUID uuid = UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8");

    SQLGetter sqlGetter = new SQLGetter(this);

    @Override
    public void setSqlGetter(SQLGetter sqlGetter) {
        this.sqlGetter = sqlGetter;
    }

    public int getDay() {
        return sqlGetter.getInt(uuid, new MysqlValue("DAY"));
    }

    public String getSeason() {
        return sqlGetter.getString(uuid, new MysqlValue("SEASON"));
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setSeason(String s) {
        sqlGetter.setString(new MysqlValue("SEASON", uuid, s));
    }

    public void setDay(int i) {
        sqlGetter.setInt(new MysqlValue("DAY", uuid, i));
    }

    @Override
    public void connect() {
        super.connect();
        if(this.isConnected()) sqlGetter.createTable("mafana_seasons", new MysqlValue("SEASON", ""), new MysqlValue("DAY", 0)
        , new MysqlValue("SERVER", UUID.randomUUID()));
        if(!sqlGetter.exists(uuid)) {
            sqlGetter.setString(new MysqlValue("SEASON", uuid, MafanaSeasons.getInstance().getSeasonList().get(0).getName()));
            sqlGetter.setInt(new MysqlValue("DAY", uuid, 0));
            sqlGetter.setUUID(new MysqlValue("SERVER", uuid, uuid));
        }
    }

    @Override
    public SQLGetter getSqlGetter() {
        return sqlGetter;
    }
}
