package com.singsong.erp.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DECUtil {
	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D',  
	        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',  
	        'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',  
	        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',  
	        'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',  
	        '4', '5', '6', '7', '8', '9', '+', '/', };  
	  
	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1,  
	        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
	        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
	        -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,  
	        60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,  
	        10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,  
	        -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,  
	        38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,  
	        -1, -1 };  
	/** 
	 * 解密 
	 * @param str 
	 * @return 
	 */  
	public static byte[] decode(String str) {  
	    byte[] data = str.getBytes();  
	    int len = data.length;  
	    ByteArrayOutputStream buf = new ByteArrayOutputStream(len);  
	    int i = 0;  
	    int b1, b2, b3, b4;  
	  
	    while (i < len) {  
	        do {  
	            b1 = base64DecodeChars[data[i++]];  
	        } while (i < len && b1 == -1);  
	        if (b1 == -1) {  
	            break;  
	        }  
	  
	        do {  
	            b2 = base64DecodeChars[data[i++]];  
	        } while (i < len && b2 == -1);  
	        if (b2 == -1) {  
	            break;  
	        }  
	        buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));  
	  
	        do {  
	            b3 = data[i++];  
	            if (b3 == 61) {  
	                return buf.toByteArray();  
	            }  
	            b3 = base64DecodeChars[b3];  
	        } while (i < len && b3 == -1);  
	        if (b3 == -1) {  
	            break;  
	        }  
	        buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));  
	  
	        do {  
	            b4 = data[i++];  
	            if (b4 == 61) {  
	                return buf.toByteArray();  
	            }  
	            b4 = base64DecodeChars[b4];  
	        } while (i < len && b4 == -1);  
	        if (b4 == -1) {  
	            break;  
	        }  
	        buf.write((int) (((b3 & 0x03) << 6) | b4));  
	    }  
	    return buf.toByteArray();  
	} 
	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null) {
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}	
	public static void main(String[] args) {
		System.out.println(generateImage("iVBORw0KGgoAAAANSUhEUgAAAJoAAACnCAYAAADpG0VkAAAWkklEQVR4Ae1dOXAk1ZY9uVRl7arSvvbe/E8HE4HDxHeIwJoJbHBwcXDwYRzcAXc8HDBx8DAIDCKwJmICB+LHDJ+h1U231K1dpaX2LX+c+zLVKqm6uxZVSS3dVKSy6tXb8ryTd3svM63/+K//9DcP9vHr6jKKlTLuzl7DG4s34TkuHuY38GRnA9lEFvO5WURcF02/hUazjpbvw7JsRNwIIk4EruPAsh3YlgVuvu+j3qjj4HAPO3u7iEVd/O3WPWSTSfxjc1XaKxRLuD17A9nUGNxIBI7jAkF5qeSS/PP9luB1UDrEen4TK1tPsH2QR7FcRDKexNLkvJzpk501PN1ZR6VeRavVkjTbtuHYDq5NLeDtN/6GxYm5I4xarLfVRLlWxcrmEyyv/wnWwXEsVyscBRkH4hpxXGSSabw2dxOvLd3GeConY+cS82Bjfc1WE5VqRfq4vP5I+lOolFCtV2Fb7IuNycwE7izcxLWpRSQ8D4CNnYNdPNx4jLWdDRyWC6jUKmi0WuC5kwvu/9z/Dat7uzioVQHbwnJ+E3uNCuLRqJxsOjWGXDqHeDwO23bYdfh+XPhgwYZlWcFn6wgA+OYE/ZY5sl7HdeFbkMYPy0UUKxVMZMYRjUbhuBEhaXjCl+Poc5zRaDXA8909yOPp7oYMXLFaQi41huvTi5jJTiGTSKFSq6Jcq2B7fxfVRh2WFWAXgEGcT27VWhX7pQM82V7Hg/VHWN1+KvWQMBxgCgLHsZGKJTA1NiHtLU0tCFGixBztdbK+veI+Vraf4uH6ivSVBKNQcW0X4+ks5sansTg5L2NH4fJ0h8JoHRt7W9gvHkj77KcPQwfft2DZFtz//v1/0XBcRFMJJGIJlC0f+/UqfMdB2ovD82JwhQjmyjJ9a+/gSQD4ncDYJBjrjkTQtIDDahnrxTz2ymXk4mMYT48j5sXhuI456Q5gdqr7VUirNxsiBQ6KBTzeWsXD9cfyPRaN4cb0Eq7PLGJ+fFY0Aa/4oltGwouL1qBW8LkDIokSXgKZRFo+89ybLUrIppDi/58s48HaYyEcidpsNgV7x3EQj8aQiiUxPzGL1xZuYSY3BS/iQUhm2QZG3xcp1mg1sXOQx/+t/I5Hm6tycVAqkWCUWhPpHG7P38TNmSWQyPnCPla3nuLp7jp2DvekLs+NYDydE5JX6zWUa2XKGLAvbsO24EccNG0bDctCE0ALFlpGdsmRnWi2GiI6bTLtZYSglKNAdVwhaqLVQKlawm8bK9KhpJfCVHYGruvCpsoNT9qc+iv835ern+psr7AvUoYqrVAt0ZbAwsQ8bs/fwGxuypgctisoU2JQtVGNUT2FksaxHKQTKczmZnBjZgkkKcelVq+CavjB2iMsrz3G5t6WkI9SjINKVZuMJYUUt+duYCo7KVKNJDu50RSihCXJfl9dxqPNFewc5ikq4Fg2cuksbs1ew/XpJcQ9T9T6nxsr0vb24Y60FY96mM5OYWFiVr4/3FjB481V2LBFY5HgbtOxYbk2WpYv9hcbFl1NpgvbqbfrqDfrcGzaUA6Ca+Fkn9u/W5bo83g8gZgXEz0dZhCRTqp3Q9qw0CtwbDQpFXbFTnpMO2x/V6TLrdnr4IBz0GKRqNi0cjqWhVbLFzuLqrNWr4EXNZGhJIl5HpYmF/CXxTtCThKFthttIFHDuxsolAtoNBuCJXHNJjNi892cu4GZ7KR8p1YRAdEBQ9pka7ub+H31vqjgQqUguZJeXGxn2oa35q6LFLy/9ifuP32Ig+KhcCTlJXFjdknOjeRez2/hz7U/hbTk0Hgqi78s3gbP36V4poxucReRzBMnucze8Fuot+gANNFyGkb6vFxzmlOi+iRsji1X9NF5vkwiHmV8tT5w0Hg1/7L8d5SqZZFuyVjiyO7hxUpbyY4Zp4mSq9aoolAuigSgo0B1Fffiku/a9AIWJuYwlZ2QMWKdtINIYpE8B3mx66hhPDeKZCwudtgbN+5hJjcJ13aEsMc1ENU0/2jwFypFIcfD9Ud4tLWKcrUsNjSHl2p8kja0G8EfTx6Ieswf5lGslpGOp8Q5Yf+ommkirW6vCwkpGWkqzeWmcXPuOu7O38REehyunIFPw9V4RiQYyUXQKN3EHghUJ/W/Y9MbcnpnwCUl13EgOIi1Rk0Gg4ShnVov0mwoY3V7TQaODsD8xAyiEU8k137xUIiztbctNhmNdaoa2lXZREYkBx0JEmwtvyUSk3XTBmJ7VMkkBr1TRgBIEErNqBuVdJKKUtP3m6g3GkIu1rW1vyMGPIlB1UlHgGNNB44FKTXpFNDBoO3HOtJxquNrYl/S+6VNv7W/jZWNp2LX5Qt7cv7MN52dFFXKiAQxcdlR8RKlMy2jLn1ja4RSzRybYoAaC47d71asHR+Ky/2ZxKKtRelSa9RFJTabdTQadZQAkVZ7lEibT+Sq5+BxQGlbUYotTs5hbnxWpAhVD8kQEqJUKcGyjQc5lpgWqURPb/ugiXq1YUJJpYKEo1LxlNhS9Aqp0hoNOiY1sG2qXNp0VLe0CeNeTOy6guUA1TLqqItDUayUwJ3kpVTLJDO4O38Dd+dvgfW7ti0EpNqlOt0t7Ek71JC1Zh0MifC3/OG+6YtcRoE4ZejG2AzPVKex14LvvmQAbFG4L3cKLjevTp0dLz0a7zS+W/mWDEStVTvKRzuM+/b+jkg7EpNqiFd/NjmGUrWCP54so1AuYa90IANN9UcyUNLRKGcMi6Sh9GDo4bBUQLlSFqIxbkkPd213XbxLEp4OF0lPqcrf6Wwwjartr4t3MJ2bktgeDfyN/CYKZT/wXG2JiabiSfGOb89dx9z4jHiV1HZUswxtrO1uiAdarfE8TUiGEpj7P1b+EI7QqXCpMiXmQqMUlGghyQJnQJwCk+6TiYzPiF9qy/8jFPWDhHJoUxm7JiEqkYSgzStq7hhG4XdKNF79zwLdgBeJiNSghGNQ9Nr0HNLxNKKRqBDFq9fELqKKZWiBcTqGO6j2aN6wvWq9bnwt+rAWQ6S2eK30DOnBsp9jyYyEU0hm2tIkItUrQzO09zLxtKhJOiPTY5PGo7VscQxps5dq5UDlNoVDx05PPoaqnE4B/WujOqkuxQMypDKSjGEN7qFKNXacTQkompOFVYWGAPPKpYSinZRLZcVgXt1Zw+betkigWr1+ZPuyDJFjEJxqiIPCWBmDolNjkzKwDOpSmtHbZEQ+DHvQvsu6rhCGpHAdG3sFIwE5VqyYxKXnytkc1ktpRJLRGaH0jHAmxmb8EuJdxqIe0om02GSUfuz/dHZCJBjPhxdP6FTwPIVE0Ti8qCcqmNyhRKOtGIt4oCRkGGdhck6C0kc2GiUVAq+z2fLFzRYvlFdJINVIPko/kWoWoyS28JSgBco0xPxqHjm4QTwsl3KOgqWMeTGudkDpVS6K6iNA9NZIInpuHBjOEHCPewkw+Mm6hFwnHCmHoSPYQoZ7S3dFDW4d7EhYgeqUG0lm6ssgm+I+hqSXkAAxfwtJw7xUsSQO43QkIoWLF4nKzgtBIgfH+sBwSTxqYXFqXsoxmEupzCwkGUnKNjOJjInfRen4/PubPlwXiERgRaISlEtEo8h4MaQpPr24zBDwOMbPjDZHeYVFYDkuGsaXgJmZk3PUfycQ4MXJoCwNcO4cSLNR6jhiM1G6UAr1HLymJoKZV6bqozDgRoKSPNylXsquY2QJOjDQgeqfU2x0NkJTgHZneLHxc7gdqU5hSqA+jQTj1ATttSDMQdst3Fuc+wJ4vcn8JeWZirQQ01NHCVBLONERCXYqwyAJQaySkpH7KDfjZZtFFS9r1xaVyRAHmSaT4SQXmcqVASSbCeCKzRak8TNn4shSTs12NVPwsp7o75caAYbshWBmaiCIn4WSi0d6ouFMQZBONUBRSVshYoerNy41TnpyAyJgrHmRaCQcpZghlpFmTbSaRmU2qI+PQh3BEhaSDUBU1ilZMlP/TCsP2DMtfqkQsI8MxCOyPZNqlFyhXWY80CD0Id4po25mfsBzHERtE+C7VOjoyZwZAoEzEMQngoWKVIuhujTznoFTINLON+pUSGjsNEo0ijOW4yoQ4eyZdVErugwIGDuezAiW3NIVN+44ZHL9eBA3lG5CwkDNEgQuzog7DiJ2H5PtlwFFPYeXIiCxOMklgV0TphCJFq7mEAfAeJ8hwcJ1aiaA68Pygaht1CejxlxqprbaS7G/UhlsQ4nA8wxnBySk8Sy08YxgoQdKX9TMGNCOk9kHTj9YjEgrxa4Ug7o82We3wARhDkozS8gTrrQNHACRcCZNSMYYG+iJtlBrNcE53HKriVpw906X7Wu2K4IAF6mb5bVywkayhXcvGYcg8DyDsEcjCOKa+JqPaquFSr2OltWQyQEj3a4IenqaXSPA2VWzjiCYHDALIbnSMpgZoI3GWBpX3pJsvK9T7DYLTctGjfMCOv3UNeBXNaOJo3HyUzzP0FYzAVkh1bHVG4y5ccI0EU3Ai8ZgORFD0mOTp1cVSD3vFyPA22OMRDoimlkGJDExsdU41eQg7nrIxJKYSOaQiSVkLdPJ5SMvbkp/vcoIuLydXSbUiYKQzRCPq7W5iI0L3sYSKSxmpzGTzsl6o6hIsqsMm557rwhwpU+w1tMUNfdHczGci7FYErcn5nAjN4P5TO7Z/Yi9tqL5rzwCLjh9ZHGxHFdh8P7ACOKxBJYmZvHGwm3MZycQsV1Zbnzl0VIA+kbAzaUyqFcqaDbq4Ary61MLuLt4E/O5KVn7HnejQZhfA7F9o6wF4f7bG/8KPoikUCoi4cUwPz6FybFxuT9R7sxRj1JpcgYIuK/NXg8m0U1Igw6AEExnK88AXq0iRMA9fqME78qhgpRpKOY44SiEhfSoCPSKgLv89KG5iZRPBZSnN4bHiNydw7t0Ot3y1WtDmv9qI+Dy6TfmNi8uxabatOUJfeYzHz3lCAH5hBg+OIT3+5mj+c67pzlbwOVBR6t1rzamevYdEHD5WCGGNeRG0eApjbyviXdGk4Bis9l8WIghoRDQCT+b+xDVaeiArCa1IeDyLmqjMnmzqYuIzfv0AikmT5KJyjMXSDDdFIF+EXDfvP0v/ZbVcopA1wi46d39rjNrRkUgRODOnTvhx7bj/fv3276HX9znFQgz6FEROInA88gU5uvEKTW8QnT0OFQElGhDhVcrDxFQooVI6HGoCCjRhgqvVh4ioEQLkdDjUBFQog0VXq08RECJFiKhx6EioEQbKrxaeYiAEi1EQo9DRUCJNlR4tfIQASVaiIQeh4qAEm2o8GrlIQJKtBAJPQ4VASXaUOHVykMElGghEnocKgJKtKHCO5rKq9UqSiW+evbZ1int2a+j/6REGz3mZ9oiCVUoFNqI1intTBvtozIlWh+gXYQi8vy6VgsHBweoVCpyt1qntIvQV/bh2MOSL0qXtB/dIEBSbW1toV6vI5VKIZPJyBMGTqZ1U9co8ijRRoHyGbdB1UhJRpKRYPF4XD6fTOM9uRdluzg9uSiIXPB+hPZXrVYTSUaS8T1dtNOOp/FV1hdpU6JdpNHooi+0x8rlsjxdIJ1Oy7vRO6V1UdVIsyjRRgr34I2Zx1eYYWs2m+bJT8GjK1h7mDZ4S2dbgxLtbPEcem00/MfGxkRdbm9vg6q0U9rQO9JjA0q0HgE77+w08GOxGHK5nHQlDG+cTDsZwD3vfqvXed4j0Ef7juOIp0k1SUIVi0WphY7BybREItFHC2dfRCXa2WM6khop2RjaIJFCcnVKG0lnumhEJVoXIF3kLCQbt0ajcdTNTmlHP57TByXaOQF/ls3SGTi5dUo7mWeU35Voo0R7SG11mgHolDak5ruqVm20rmDSTIMioEQbFEEt3xUCSrSuYNJMgyKgRBsUQS3fFQJKtK5g0kyDIqBEGxRBLd8VAkq0rmDSTIMioEQbFEEt3xUCSrSuYNJMgyKgRBsUQS3fFQJKtK5g0kyDIqBEGxRBLd8VAkq0rmDSTIMioEQbFEEt3xUCSrSuYNJMgyLg7u3tDVqHlr9iCPRzH4I7NTV1xWDS0x0Ugd9++63nKlR19gyZFugHAV3K3Q9qWuaFCHR6cawS7YWQ6Y/9IPD666+fKqaq8xQkmjAMBJRow0BV6zyFgBLtFCSaMAwElGjDQFXrbEOA4RAlWhsk+mVYCCjRhoWs1tuGgBKtDQ79MiwElGjDQlbrbUNAidYGh34ZFgJKtGEhq/W2IaBEa4NDvwwLASXasJDVetsQaJtU//nnn/Htt9+2ZXjnnXfw008/taUd/8KnQ3/44YeYnp4+nqyfR4wAn2PLne8hcF1XjiPuwgubcz/55BPJ8P777+Ott97CvXv38PXXX2NiYgLvvfceVldXhWj8/eRvP/74I3799dcXNqA/jgaBw8ND7O/vC8lmZ2fBJ3dfpM2lxHr33XcvUp+0Lz0ikM/njx4Bz4cmb25uYnx8HJ7n9VjT8LK77AxfVhWNRl/YynGVurKygl9++UXyhy9WeGFh/XEoCPBlY3yhBd81wJeM8d1QTKN0YzofmMx3D1yEzf3hhx/A/aOPPsKtW7ee2ydVnc+F5lx+oOTiy8ZIKtpkJFUymRQ7jWTji8m48aHJF0GyieoMjf3jzgCl1qNHj/D222+fC5Da6PMRIJFIsp2dHbHFstmskInkozNAtcnf+EYV5p2cnBTC8bfz2tzl5WV89tlncjVQop00+OkMcFPVeV5DdLpdvpuThj8Nfnr7VJuhuqQEm5mZkXdF8TPTNzY2JO08HYS28MbpU4Ko0y+++EJ+4hVy3CN9mV3XqT5NGxwB2l1Ul9xIMtpjHJswxMG33lHK0Wbjy8i4nfd7B15KNOml/rtQCJBc3KkWKd1IMhKJr+YJ0yjJSLR+bvYdxsm6H3/88QvrffDgAb788su2PCe9Tg3YtsEzki+UXnxXJ580QJVIZ4A700m20BmgXXYRNI8EbJ/ncZJk7KSqzpFwp+tGfN8Xkm1tbUkZBtdDyUXS8ekD/C1Up7TjztMRYCdPzXXScAwlFu0x3S4eAlSLu7u7oi5p+Id22PGeMr5JCUepxzGlpDvPrc1G+/7772W66c0335TpJ4rlr776Cow8H99CIoZpYf6LIKLDPl3mI4lFCRWqxU6GPp0F2mgckzDveWJifffdd/55dkDbfvUQ4F1Nd+7c6dhxPg7h5J3qehdUR6g0cRgInLLRhtGI1qkIKNGUAyNBQIk2Epi1ESWacmAkCCjRRgKzNqJEUw6MBAEl2khg1kaUaMqBkSCgRBsJzNqIEk05MBIElGgjgVkbUaIpB0aCgBJtJDBrI0o05cBIEHDD5cAjaU0bubII6NvtruzQ93/ivJ2v101VZ6+Iaf6+EFCi9QWbFuoVASVar4hp/r4QUKL1BZsW6hUBJVqviGn+vhBQovUFmxbqFQElWq+Iaf6+EFCi9QWbFuoVAfubb76RZ9j2WlDzKwK9IKASrRe0NG/fCCjR+oZOC/aCgBKtF7Q0b98IKNH6hk4L9oKAEq0XtDRv3wgo0fqGTgv2goASrRe0NG/fCLgffPBB34W1oCLQLQL2559/Lm9D67aA5lME+kHA/fTTT/spp2UUgZ4QUButJ7g0c78I2HwDMR/7rpsiMEwE3PCtKMNsROtWBFR1KgdGgoASbSQwayNKNOXASBBQoo0EZm1EiaYcGAkCSrSRwKyNKNGUAyNBQIk2Epi1ESWacmAkCCjRRgKzNqJEUw6MBIF/Ajhov9FHpwpSAAAAAElFTkSuQmCC","d:/a.jpg"));
	}
}
