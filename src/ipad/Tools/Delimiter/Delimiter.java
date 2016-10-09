package ipad.Tools.Delimiter;

public class Delimiter {
	
	private static final byte[] RMC_TAG = {(byte) 'R', (byte) 'M', (byte) 'C'};
	
	
	/**
	 * $GPRMC,081836,A,3751.65,S,14507.36,E,000.0,360.0,130998,011.3,E*62
	 */
	private static final byte[] exampleBuffer = {
			(byte) '$', (byte) 'G', (byte) 'P', (byte) 'R', (byte) 'M', (byte) 'C', (byte) ',', (byte) '0', (byte) '8', (byte) '1', 
			(byte) '8', (byte) '3', (byte) '6', (byte) ',', (byte) 'A', (byte) ',', (byte) '3', (byte) '7', (byte) '5', (byte) '1', 
			(byte) '.', (byte) '6', (byte) '5', (byte) ',', (byte) 'S', (byte) ',', (byte) '1', (byte) '4', (byte) '5', (byte) '0',
			(byte) '7', (byte) '.', (byte) '3', (byte) '6', (byte) ',', (byte) 'E', (byte) ',', (byte) '0', (byte) '0', (byte) '0', 
			(byte) '.', (byte) '0', (byte) ',', (byte) '3', (byte) '6', (byte) '0', (byte) '.', (byte) '0', (byte) ',', (byte) '1', 
			(byte) '3', (byte) '0', (byte) '9', (byte) '9', (byte) '8', (byte) ',', (byte) '0', (byte) '1', (byte) '1', (byte) '.', 
			(byte) '3', (byte) ',', (byte) 'E', (byte) '*', (byte) '6', (byte) '2'};
	
	private static byte[] workBuffer = new byte[20];
	
	private static short delimitVal = (short) 2;
	
	private static short NMEADataOffset;
	private static short NMEADataLength;
	
	public static void processNMEA(byte[] buffer, short offset, short length) {
		
		short dataOffset = (short) 0;
		short dataLen = (short) 0;
		short delimitOffset = (short)0;
			
		short tempOffset = 0;
		
		while(tempOffset < length) {
			
			if( dataOffset != 0 ) {
				dataLen++;
			}
			
			if( buffer[tempOffset] == ',')  {
				
				if( delimitVal != 0 ) 
					delimitOffset++;
				
				
				if ( delimitOffset == delimitVal )
					dataOffset = (short) (tempOffset + 1); 
				else if (delimitOffset > delimitVal) {
					dataLen--;
					break;
				} 
				
			}
			
			
			
			tempOffset++;
		}
		
		System.out.println(dataOffset);
		System.out.println(dataLen);
		System.out.println("-----------");
		
		
		
	}
	
	private static void getDelimiterData(short delimitterOffset, short dataLen, byte[] srcBuff, 
			short srcOffs, short srcLen, byte[] desBuff, short destOffset) {
			
		short dataOffset = (short) 0;
		short delimitOffset = (short)0;
		short tempOffset = 0;
		
		dataLen = (short) 0;
		
		while(tempOffset < srcLen) {
			
			if( dataOffset != 0 ) {
				dataLen++;
			}
			
			if( srcBuff[tempOffset] == ',')  {
				
				if( delimitVal != 0 ) 
					delimitOffset++;
				
				
				if ( delimitOffset == delimitVal )
					dataOffset = (short) (tempOffset + 1); 
				else if (delimitOffset > delimitVal) {
					dataLen--;
					break;
				} 
				
			}
			tempOffset++;
		}
		
	
		setNMEALength(dataLen);
		setNMEAOffset(dataOffset);
		
	}
	
	public static void setNMEAOffset(short data) {
		NMEADataOffset = data;
	}
	
	public static void setNMEALength(short data) {
		NMEADataLength = data;
	}
	
	public static void main(String[] args) {

		
		short offset;
		short length = 0;
		
		getDelimiterData((short) 3, (short) length, exampleBuffer, (short) 0,
				(short) exampleBuffer.length, workBuffer, (short) 0);
		
		System.out.println(NMEADataOffset);
		System.out.println(NMEADataLength);
		System.out.println("*******");
		
		processNMEA(exampleBuffer, (short) 0, (short) exampleBuffer.length);
		
		
		
		System.out.println("done");
	}
}
