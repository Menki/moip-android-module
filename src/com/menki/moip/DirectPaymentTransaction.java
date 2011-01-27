/**
 * Menki Mobile Solutions
 * http://www.menkimobile.com.br
 * 
 * @author Gustavo Scaramelli
 *
 */
//package com.menki.moip;
//
//
//public class DirectPaymentTransaction extends Activity
//{
//
//	
//	public DirectPaymentTransaction( ) 
//	{
//		PaymentMgr mgr = PaymentMgr.getInstance( );
//		int ErrorCode = mgr.performDirectPaymentTransaction( );
//		
//		//TODO: Call handler
//		
//		switch(ErrorCode)
//		{
//			case 100: //O envelope XML da Instrução não foi enviado corretamente
//
//				break;
//			default:
//				break;
//		}
//		
//		progDailog = ProgressDialog.show(curContxt,
//				"Progress dialogue sample ", "ceveni.com please wait....",
//				true);
//				new Thread() {
//				public void run() {
//				try{
//				// just doing some long operation
//				sleep(5000);
//				} catch (Exception e) { }
//				handler.sendEmptyMessage(0);
//				progDailog.dismiss(); }
//				}.start();
//				}//
//
//				private Handler handler = new Handler() {
//				@Override
//				public void handleMessage(Message msg) {
//				txt.setText("Processing Done");
//
//				}
//		
//	}
//
//}
