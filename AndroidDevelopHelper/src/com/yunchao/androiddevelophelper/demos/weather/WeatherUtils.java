package com.yunchao.androiddevelophelper.demos.weather;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;
import org.xmlpull.v1.XmlPullParserException;

/*�ο���Ϣ
 * 
 * String(0) �� String(4)��ʡ�ݣ����У����д��룬����ͼƬ��ƣ�������ʱ�䡣
 * String(5) �� String(11)������� ���£��ſ�������ͷ������������ƿ�ʼͼƬ���(���³ƣ�ͼ��һ)��
 * �������ƽ���ͼƬ���(���³ƣ�ͼ���)�����ڵ�����ʵ������������ָ��String(12) �� String(16)��
 * �ڶ���� ���£��ſ�������ͷ�����ͼ��һ��ͼ�����String(17) �� String(21)��
 * ������� ���£��ſ�������ͷ�����ͼ��һ��ͼ�����String(22) ����ѯ�ĳ��л����Ľ���
 */

/*ʵ�ʷ������
 * 
 * anyType{string=�㶫; string=÷��; string=59117; string=59117.jpg; string=2013-5-30 9:47:57; 
 * string=25��/35��; string=5��30�� ��ת����; string=�޳������΢��; string=0.gif; string=1.gif; 
 * string=��������ʵ�������£�29�棻����/�������Ϸ� 2����ʪ�ȣ�69%���������������ޣ�������ǿ�ȣ�����; 
 * string=����ָ�����ޡ����������и���ָ���������ָ�����ޡ�; 
 * string=25��/35��; string=5��31�� ��; string=�޳������΢��; string=0.gif; string=0.gif; 
 * string=25��/35��; string=6��1�� ��; string=�޳������΢��; string=0.gif; string=0.gif; 
 * string=÷����λ�ڹ㶫ʡ�������������ڸ���ʡ����ƽ���Ϻ���������ƽ��4�أ������ӽ���ʡѰ���أ�
 * �������㶫ʡ��Դ�е����ء���Դ�ء��Ͻ��أ����ϡ���������β�е�½���ء������е��ų����������ӣ�
 * ������ͳ����н�����ƽ��������ȫ���������λ�ڶ���115��18����116��56������γ23��23����24��56��֮�䣬
 * ȫ�������15836ƽ�����÷���������ȴ��������������ȴ�������ȴ������Ĺ�ɵش�ƽԶ��
 * �����÷�ر���Ϊ�����ȴ��������Ե���廪����˳�����������Һ�ƽԶ�����롢÷���ϲ�Ϊ�����ȴ������
 * ���ֵش���γ�������Ϻ���̫ƽ���ɽ�ص��ض�����Ӱ�죬�γ����ճ������ն̣����¸ߡ��������⡢���ճ��㡢
 * ����������ˮ��ӯ�Ҽ��е����÷���н϶����ʤ�ż������ľ��ۡ�ʼ�����ƴ��ǧ���ɲ����¡�����ɽ��ָ�塢
 * �ɿ�Ԫ����������ٲ������볤̶һ���졢������ˮˮ�⡢���ɽ�����ҷ�Ϫ��Ȼ������ƽԶ��̨ɽ����ָʯ���廪����ˮ�⡢
 * ��˳��Ȫ��ǧ�����Ⱦ��㣬�������ۡ��桢�ġ���֮��ɫ��������ο͡���ʷ���������顢ף֦ɽ�����������Щ��ʤ�����㼣��; }
 */

public class WeatherUtils {
	// ͨ��WebXml��ȡ����Ԥ������Ҫ�Ĳ����Լ�ʵ�ֺͽ����ķ���.
	/** ����ռ� */
	private static final String NAMESPACE = "http://WebXml.com.cn/";
	/** ��ȡ�����WebServiceURL */
	private static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
	/** ���÷��� */
	private static final String METHOD_NAME = "getWeatherbyCityName";
	/** ����ռ�+������ */
	private static String SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";// ͨ��������ֻ�ȡ����

	/**
	 * ����������ϸ��Ϣ
	 */
	public static String[] getWeather(String cityName) throws IOException,
			XmlPullParserException {
		// �����ȡ������Ϣ
		SoapObject detail = null;

		// 1.ʵ��SoapObject����
		SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
		// 2.�����Ҫ�������ò���
		soapObject.addProperty("theCityName", cityName);

		// 3.����Soap��������Ϣ,������л�envelope,�����ΪSoapЭ��İ汾��
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		// 4.�����������
		int timeout = 20000;// ���ó�ʱΪ20�롣�����û������������粻��ʱ������10�������쳣
		MyAndroidHttpTransport httpTransportSE = new MyAndroidHttpTransport(URL, timeout);
		httpTransportSE.debug = true;
		// 5.����WebService,��һ������Ϊ����ռ� + ������,�ڶ�������ΪEnvelope����
		httpTransportSE.call(SOAP_ACTION, envelope);

		detail = (SoapObject) envelope.getResponse();// ��ȡ��ϸ������Ϣ
		if (detail != null) {// ��ǰ������������Ϣ
			return parseWeather(detail);//��������
		}
		return null;
	}
	
	/**��������SoapObject���ת��ΪString[]*/
	private static String[] parseWeather(SoapObject detail)
			throws UnsupportedEncodingException {
		String[] weather = new String[23];// һ����23�����
		for (int i = 0; i < 23; i++) {
			weather[i] = detail.getPropertyAsString(i);
		}
		return weather;
	}
}

//�̳�HttpTransportSE��ʹ�ó�ʱ������Ч
class MyAndroidHttpTransport extends HttpTransportSE {
	private int timeout = 20000; // Ĭ�ϳ�ʱʱ��Ϊ20s

	public MyAndroidHttpTransport(String url) {
		super(url);
	}

	public MyAndroidHttpTransport(String url, int timeout) {
		super(url);
		this.timeout = timeout;
	}

	// �˷���ʹ�ó�ʱ��Ч
	public ServiceConnection getServiceConnection() throws IOException {
		ServiceConnectionSE serviceConnection = new ServiceConnectionSE(this.url,
				timeout);
		return serviceConnection;
	}
}
