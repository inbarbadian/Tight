package hackidc.com.tight;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

/**
 * This is a documentation by Doron Passal the author of this code
 */
public class MainFragment extends Fragment {

    ImageView heartImg;
    ImageView negativeImg;

    int totalMocks = 4;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_new, container, false);

        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        CardStackLayoutManager layoutManager = new CardStackLayoutManager(getContext(), new MyCardStackListener());
        cardStackView.setLayoutManager(layoutManager);
        layoutManager.setCanScrollVertical(false);
        layoutManager.setMaxDegree(25.0f);
        layoutManager.setSwipeThreshold(0.5f);
        cardStackView.setAdapter(new CardsAdapter());

        heartImg = view.findViewById(R.id.heart_img);
        negativeImg = view.findViewById(R.id.negative_img);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class CardsAdapter extends RecyclerView.Adapter<ViewHolder> {

        int count = totalMocks;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View view = inflater.inflate(R.layout.list_item, viewGroup, false);

            // Return a new holder instance
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (i == 0) {
                Picasso.get().load(R.drawable.test_1).fit().into(viewHolder.imageView);
                viewHolder.setBadges(R.drawable.long_sleeve_shirt, R.drawable.shoes);
            } else if (i == 1) {
                Picasso.get().load(R.drawable.test_2).fit().into(viewHolder.imageView);
                viewHolder.setBadges(R.drawable.dress, R.drawable.hills);
            } else if (i == 2) {
                Picasso.get().load(R.drawable.test_3).fit().into(viewHolder.imageView);
                viewHolder.setBadges(R.drawable.dress);
            } else if (i == 3) {
                Picasso.get().load(R.drawable.test_4).fit().into(viewHolder.imageView);
                viewHolder.setBadges(R.drawable.long_sleeve_shirt, R.drawable.pants);
            }
        }

        @Override
        public int getItemCount() {
            return count;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView badge1;
        ImageView badge2;
        ImageView badge3;
        ImageView badge4;
        ImageView badge5;
        ImageView[] badges;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            badge1 = view.findViewById(R.id.badge1);
            badge2 = view.findViewById(R.id.badge2);
            badge3 = view.findViewById(R.id.badge3);
            badge4 = view.findViewById(R.id.badge4);
            badge5 = view.findViewById(R.id.badge5);
            badges = new ImageView[] {badge1, badge2, badge3, badge4, badge5};
        }

        void setBadges(int... ids) {
            for (int i = 0; i < ids.length; i++) {
                this.badges[i].setImageResource(ids[i]);
                this.badges[i].setVisibility(View.VISIBLE);
            }
            for (int i = ids.length; i < 5; i++) {
                this.badges[i].setVisibility(View.GONE);
            }
        }
    }

    public class MyCardStackListener implements CardStackListener {

        boolean noMoreCards = false;

        @Override
        public void onCardDragging(Direction direction, float ratio) {
            if (!noMoreCards && direction.equals(Direction.Right)) {
                heartImg.setAlpha(ratio);
            } else if (!noMoreCards && direction.equals(Direction.Left)) {
                negativeImg.setAlpha(ratio);
            }
        }

        @Override
        public void onCardSwiped(Direction direction) {

        }

        @Override
        public void onCardRewound() {

        }

        @Override
        public void onCardCanceled() {
            heartImg.setAlpha(0f);
            negativeImg.setAlpha(0f);
        }

        @Override
        public void onCardAppeared(View view, int position) {
            // make the reactions images appear for 300 ms, then hide
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }

                    Activity activity = MainFragment.this.getActivity();
                    if (activity == null) {
                        return;
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            heartImg.setAlpha(0f);
                            negativeImg.setAlpha(0f);
                        }
                    });
                }
            };
            thread.start(); //start the thread

        }

        @Override
        public void onCardDisappeared(View view, int position) {
            if (position + 1 == totalMocks) {
                //Last Card disappeared
                heartImg.setAlpha(0f);
                negativeImg.setAlpha(0f);
                noMoreCards = true;
            }
        }
    }

}
