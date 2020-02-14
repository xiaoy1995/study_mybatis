package site.aiduoduo.mybatis.mapping;

/**
 * @Author yangtianhao
 * @Date 2020/2/7 8:16 下午
 * @Version 1.0
 */
public class MappedStatment {
    private final String id;
    private final String parameterType;
    private final String resultType;
    private final String statementType;
    private final SqlSource sqlSource;

    public MappedStatment(String id, String parameterType, String resultType, SqlSource sqlSource,String statementType) {
        this.id = id;
        this.parameterType = parameterType;
        this.resultType = resultType;
        this.sqlSource = sqlSource;
        this.statementType = statementType;
    }

    public String getId() {
        return id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public String getStatementType() {
        return statementType;
    }
}
