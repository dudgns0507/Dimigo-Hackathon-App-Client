package kr.hs.dimigo.dudgns0507.hongikbook.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import kr.hs.dimigo.dudgns0507.hongikbook.Data.BookFull;
import kr.hs.dimigo.dudgns0507.hongikbook.Interface.AddBook;
import kr.hs.dimigo.dudgns0507.hongikbook.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pyh42 on 2016-12-10.
 */

public class AddBookCheckFragment extends DialogFragment {

    private static final String TAG = "AddBookCheckFragment";

    @Override
    public void onStart() {
        super.onStart();

        Display display = getActivity().getWindowManager().getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.8);
        int height = (int) (display.getHeight() * 0.8);

        Log.w(TAG, width + ":" + height);

        getDialog().getWindow().setLayout(width, height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.fragment_bookcheck_dialog, container);

        final Bundle mArgs = getArguments();
        byte[] byteArray = mArgs.getByteArray("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView book_image = (ImageView)view.findViewById(R.id.book_image);
        book_image.setImageBitmap(bitmap);

        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(mArgs.getString("title"));

        TextView author = (TextView)view.findViewById(R.id.author);
        author.setText("저자 : " + mArgs.getString("author"));

        TextView publisher = (TextView)view.findViewById(R.id.publisher);
        publisher.setText("출판사 : " + mArgs.getString("publisher"));

        TextView price = (TextView)view.findViewById(R.id.price);
        price.setText("가격 : " + mArgs.getString("price"));

        TextView description = (TextView)view.findViewById(R.id.description);
        description.setText("소개 : " + mArgs.getString("description"));

        Button pos = (Button)view.findViewById(R.id.positive_btn);
        Button neg = (Button)view.findViewById(R.id.negative_btn);

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .readTimeout(20, TimeUnit.SECONDS)
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.api_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

                AddBook addBook = retrofit.create(AddBook.class);

                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("type", 1);
                data.put("title", mArgs.getString("title"));
                data.put("ISBN", mArgs.getString("isbn"));
                data.put("author", mArgs.getString("author"));
                data.put("publication", mArgs.getString("publication"));
                data.put("publisher", mArgs.getString("publisher"));
                data.put("price", mArgs.getString("price"));
                data.put("description", mArgs.getString("description"));
                data.put("owner_serial", mArgs.getString("owner_serial"));
                data.put("rental", true);
                data.put("rental_state", false);
                data.put("rental_date", null);
                data.put("rental_extension", true);
                data.put("rental_extension_state", false);
                data.put("image_url", mArgs.getString("image_url"));

                final ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("등록 중입니다..");
                dialog.show();

                Call<BookFull> call = addBook.push(data);
                call.enqueue(new Callback<BookFull>() {
                    @Override
                    public void onResponse(Call<BookFull> call, Response<BookFull> response) {
                        if (response.body().getId() != null) {
                            Toast.makeText(getContext(), "등록 되었습니다.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
                            AddBookCheckFragment.this.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookFull> call, Throwable t) {
                        Toast.makeText(getContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });
        neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBookCheckFragment.this.dismiss();
            }
        });
        return view;
    }
}
