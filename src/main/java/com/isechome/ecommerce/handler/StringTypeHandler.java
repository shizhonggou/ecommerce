package com.isechome.ecommerce.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description 数据库编码转换
 * @Author zhaofy
 * @Date  2021/3/17
 * @Param
 * @return
 **/
public class StringTypeHandler implements TypeHandler<Object> {

	private static String ISO88591_ENCODE = "ISO8859_1";
	private static String UTF8_ENCODE = "UTF-8";
	private static String GBK_ENCODE = "GBK";

	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		try {
			String paramStr = null;
			if (null != parameter) {
				paramStr = (String) parameter;
				if (GBK_ENCODE.equals(getEncode(paramStr))) {
					if (null != parameter) {
						ps.setString(i, new String(((String) parameter).getBytes(GBK_ENCODE), ISO88591_ENCODE));
					}
				}
			} else {
				ps.setString(i, (String) parameter);
			}
		} catch (UnsupportedEncodingException e) {
			ps.setString(i, (String) parameter);
		}
	}

	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		String strResult = null;
		try {
			if (null != rs.getString(columnName)) {
				String columnValue = rs.getString(columnName);
				if (ISO88591_ENCODE.equals(getEncode(columnValue))) {
					strResult = new String(rs.getString(columnName).getBytes(ISO88591_ENCODE), GBK_ENCODE);
				} else {
					strResult = rs.getString(columnName);
				}
			} else {
				strResult = rs.getString(columnName);
			}
		} catch (UnsupportedEncodingException e) {
		}
		return strResult;
	}

	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getString(columnIndex);
	}

	private String getEncode(String str) {
		String encode = null;
		if (verifyEncode(str, GBK_ENCODE)) {
			encode = GBK_ENCODE;
		} else if (verifyEncode(str, ISO88591_ENCODE)) {
			encode = ISO88591_ENCODE;
		} else if (verifyEncode(str, UTF8_ENCODE)) {
			encode = UTF8_ENCODE;
		}

		return encode;
	}

	private boolean verifyEncode(String str, String encode) {
		boolean verifyEncode = false;
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				verifyEncode = true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return verifyEncode;
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {

		return null;
	}
}
