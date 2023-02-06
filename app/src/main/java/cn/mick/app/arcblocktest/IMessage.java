package cn.mick.app.arcblocktest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * create by Mick when 2023/2/4.
 */
class IMessage implements Parcelable {

   public String content;

   public String what;


   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.content);
      dest.writeString(this.what);
   }

   public void readFromParcel(Parcel source) {
      this.content = source.readString();
      this.what = source.readString();
   }

   public IMessage() {
   }

   protected IMessage(Parcel in) {
      this.content = in.readString();
      this.what = in.readString();
   }

   public static final Creator<IMessage> CREATOR = new Creator<IMessage>() {
      @Override
      public IMessage createFromParcel(Parcel source) {
         return new IMessage(source);
      }

      @Override
      public IMessage[] newArray(int size) {
         return new IMessage[size];
      }
   };
}
