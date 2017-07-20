
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Task extends Thread{//���������̣߳����Ͻ��տͻ��˴������ֽ�
		private Socket socket;
		InputStream in;
		DataInputStream data;
		DataInputStream datain;
		ArrayList<Client> cl;
		char c;
		String s;
		String idS;
		
		public Task(ArrayList<Client> cl,Socket socket){//��Ҫ�Ĳ����Ǵ���˿ͻ��˵����黹�е�ǰ�ͻ��˵�socket
			this.cl = cl;
			this.socket = socket;
			System.out.println("�߳̿�ʼ");
			this.start();
		}
		
		public void run() {
			while(true)
			handlerSocket();
		}
		
		//���տͻ��˴�������Ϣ
		private void handlerSocket() {
			try {
				//���������������ܶ�ȡ������
				in = socket.getInputStream();
				datain = new DataInputStream(in);
				
				int chat =  datain.read();//��������
				
				switch(chat){
				case 0:{
					this.signin();//��¼��Ϣ
					break;
				}
				case 1:{
					this.toAll();//Ⱥ����Ϣ
					break;
				}
				case 2:{
					this.toOne();//˽����Ϣ
					break;
				}
		}} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
	public void toAll(){//Ⱥ�ĵķ���
		System.out.print("��������ΪȺ��   ");
		int type;
		
		try {//��Ϣ������
			type = datain.read();
			
			switch (type){//������Ϣ��ͬ���ò�ͬ�ķ�ʽ��
			
			case 1:{//����Ϣ�������ַ���ʱ
				System.out.print("Ⱥ������ΪString   ");
				int lenth = 0;
				lenth = datain.readInt();//������Ϣ�ĳ���
				System.out.print("��Ϣ�����ǣ�"+lenth);//��Ϊlenth�����������ֽ�����ĳ��ȣ����ԣ���������lenthһ��Ҫ��byte�ĳ���
				byte[] b = new byte[lenth];
				datain.read(b);//������Ϣ
				
				//toString��new String�����𣿣�
				String s = new String(b);
				System.out.println("   Ⱥ����ϢΪ��"+s);
				
				if(b!=null){//���ַ�����Ϊ��ʱ��ִ��Ⱥ������
	            	System.out.println("���������յ���"+b.toString());
	            	if(cl.size()>1){//�������Ŀͻ��˵ĸ�������һ��������Ⱥ���ı�Ҫ
	            		for(int i=0;i<cl.size();i++){
	            			if(!socket.equals(cl.get(i).getSocket()) && cl.get(i)!=null){//�����Լ�֮��Ŀͻ���
	            				DataOutputStream dataout = new DataOutputStream(cl.get(i).getSocket().getOutputStream());
	            				try{
	            				//���͵��ͻ��˵�ҲҪ����Э�飬����Ҫ�Ƿ����������ݵ��ֽڳ���
	            				dataout.writeInt((idS+":"+s).getBytes().length);
	            				dataout.write((idS+":"+s).getBytes());
	            				System.out.println(i+"����������ɹ�����");}
	            				catch (Exception e){
	            					cl.set(i,null);
	            				}
	            			}
	            		}
	            	}
	            }
				break;
			}
			default:{
				System.out.print("Ⱥ����Ϣ������"+type);
				int lenth = 0;
				lenth = datain.readInt();//������Ϣ�ĳ���
				System.out.print("  ��Ϣ�����ǣ�"+lenth);
				byte[] b = new byte[lenth];
				datain.read(b);
				System.out.println("��Ϣ�����ǣ�"+b);
				break;
			}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void toOne(){//˽�ĵķ���
		System.out.print("��������Ϊ˽��   ");
		int flenth;//˽�ĺ��ѵ�ID�ĳ���
		try {
			flenth = datain.readInt();
			byte[] friend = new byte[flenth];
			datain.read(friend);//���նԷ���ID
			
			String f = new String(friend);
			
			System.out.print("˽�Ķ����ǣ�"+f+"  ");
			
			int type = datain.read();//������Ϣ������
			
			switch(type){
			case 1:{//������Ϣ��������String
				System.out.print("˽������ΪString   ����Ϊ��");
				int lenth = datain.readInt();//������Ϣ�ĳ���
				byte[] mess = new byte[lenth];
				datain.read(mess);//������Ϣ
				
				String info = new String(mess);
				String s = idS+":"+info;
				System.out.println(mess.toString());
				for(int i=0;i<cl.size();i++){
					if(f.equals(cl.get(i).getId())){//�жϸ�ID�Ƿ�Ϊ���Ͷ����id
						DataOutputStream dataout = new DataOutputStream(cl.get(i).getSocket().getOutputStream());
						dataout.writeInt(s.getBytes().length);
						dataout.write(s.getBytes());
						break;
					}
				}
				break;
			}
			default:{
				
				int lenth = datain.readInt();//������Ϣ�ĳ���
				byte[] mess = new byte[lenth];
				datain.read(mess);//������Ϣ
				System.out.print("˽������Ϊ����"+type+" ����Ϊ��"+lenth+"   ����Ϊ��");
				System.out.println(mess.toString());
				break;
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void signin(){//��¼��Ϣ�ķ���
		int signin;
		try {
			signin = datain.read();
			System.out.print("��������Ϊ��¼��Ϣ   ");
			
			switch(signin){
			case 1:{//��¼��ϢΪ1 �˺�
				int lenth = datain.readInt();//��Ϣ�ĳ���
				byte[] id = new byte[lenth];
				datain.read(id);
				
				idS = new String(id);
				cl.get(cl.size()-1).setId(idS);//�Ѹ�Client���˺����ó�������˺�
				System.out.print("��¼��ϢΪ1 �˺�   �˺�Ϊ��");
				System.out.println(idS);
				break;
			}
			default:{
				int lenth = datain.readInt();//��Ϣ�ĳ���
				byte[] id = new byte[lenth];
				datain.read(id);
				String idS = new String(id);
				System.out.print("��¼��ϢΪ����  "+signin+"����Ϊ��"+lenth+" ����Ϊ��");
				System.out.println(idS);
				break;
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}



