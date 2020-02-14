package site.aiduoduo.mybatis.test;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import site.aiduoduo.mybatis.builder.XmlConfigurationBuilder;
import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.MappedStatment;
import site.aiduoduo.mybatis.mapping.ParameterMapping;
import site.aiduoduo.mybatis.pojo.User;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author yangtianhao
 * @Date 2020/1/29 4:28 下午
 * @Version 1.0
 */
public class FrameworkTest {
    @Test
    public void test() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        // 注意:使用JDBC规范，采用都是 java.sql包下的内容

        try {
            Configuration configuration = new XmlConfigurationBuilder(
                this.getClass().getClassLoader().getResourceAsStream("mybatis-config-schema.xml")).parse();

            conn = configuration.getEnvironment().getDataSource().getConnection();

            // 3 获取sql语句
            MappedStatment mappedStatment = configuration.getMappedStatment("site.aiduoduo.user.selectByPhone");
            User user = new User();
            user.setPhone("18115610044");
            BoundSql boundSql = mappedStatment.getSqlSource().getBoundSql(user);
            String sql = boundSql.getSql();
            // 4 获取预处理 statement
            if ("prepared".equals(mappedStatment.getStatementType())) {
                stmt = conn.prepareStatement(sql);
            }
            // 5 设置参数，序号从1开始
            if (CollectionUtils.isNotEmpty(boundSql.getParameterMappings())) {
                for (int i = 0; i < boundSql.getParameterMappings().size(); i++) {
                    ParameterMapping parameterMapping = boundSql.getParameterMappings().get(i);
                    String name = parameterMapping.getName();
                    Field field = user.getClass().getDeclaredField(name);
                    field.setAccessible(true);
                    Object o = field.get(user);
                    stmt.setObject(i + 1, o);
                }
            }
            // 6 执行SQL语句
            rs = stmt.executeQuery();
            // 7 处理结果集
            while (rs.next()) {
                // 获得一行数据
                System.out.println(rs.getString("name") + ", " + rs.getString("gender") + "," + rs.getString("phone")
                    + "," + rs.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
