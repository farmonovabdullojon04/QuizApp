package com.abdullojon.quizapp.start.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abdullojon.quizapp.R;
import com.abdullojon.quizapp.data.UserPreferences;
import com.abdullojon.quizapp.quiz.view.QuizActivity;
import com.abdullojon.quizapp.start.StartContract;
import com.abdullojon.quizapp.start.presenter.StartPresenterImpl;
import com.abdullojon.quizapp.view.CircularScoreView;

import java.util.List;

public class StartActivity extends AppCompatActivity implements StartContract.View {
    private StartContract.Presenter presenter;
    private UserPreferences userPreferences;

    private Button btnStart;
    private Button btnResume;
    private View resumeCard;

    private TextView tvUserScore;
    private EditText etSearch;

    private CircularScoreView scoreViewAct1;
    private CircularScoreView scoreViewAct2;
    private CircularScoreView scoreViewAct3;

    private ImageView iconAct1, iconAct2, iconAct3;
    private TextView tvTitleAct1, tvTitleAct2, tvTitleAct3;

    private View catLogic, catPuzzle, catWisdom, catTopqirlik, catRiddles;
    private View itemAct1, itemAct2, itemAct3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

        presenter = new StartPresenterImpl(this);
        userPreferences = new UserPreferences(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupListeners();
        setupSearchFilter();
    }

    private void initViews() {
        btnStart = findViewById(R.id.btnStart);
        btnResume = findViewById(R.id.resume);
        resumeCard = findViewById(R.id.resumeCard);

        tvUserScore = findViewById(R.id.tvUserScore);
        etSearch = findViewById(R.id.etSearch);

        scoreViewAct1 = findViewById(R.id.scoreViewAct1);
        scoreViewAct2 = findViewById(R.id.scoreViewAct2);
        scoreViewAct3 = findViewById(R.id.scoreViewAct3);

        iconAct1 = findViewById(R.id.iconAct1);
        iconAct2 = findViewById(R.id.iconAct2);
        iconAct3 = findViewById(R.id.iconAct3);

        tvTitleAct1 = findViewById(R.id.tvTitleAct1);
        tvTitleAct2 = findViewById(R.id.tvTitleAct2);
        tvTitleAct3 = findViewById(R.id.tvTitleAct3);

        catLogic = findViewById(R.id.catLogic);
        catPuzzle = findViewById(R.id.catPuzzle);
        catWisdom = findViewById(R.id.catWisdom);
        catTopqirlik = findViewById(R.id.catTopqirlik);
        catRiddles = findViewById(R.id.catRiddles);

        itemAct1 = findViewById(R.id.itemActivity1);
        itemAct2 = findViewById(R.id.itemActivity2);
        itemAct3 = findViewById(R.id.itemActivity3);
    }

    private void setupListeners() {
        if (btnStart != null) {
            btnStart.setOnClickListener(v -> launchCategoryQuiz(0, "Mantiq"));
        }
        if (btnResume != null) {
            btnResume.setOnClickListener(v -> presenter.onResumeQuiz());
        }

        // Category click listeners
        setupCategoryClick(catLogic, 0, "Mantiq");
        setupCategoryClick(catPuzzle, 1, "Boshqotirma");
        setupCategoryClick(catWisdom, 2, "Zukkolik");
        setupCategoryClick(catTopqirlik, 3, "Topqirlik");
        setupCategoryClick(catRiddles, 4, "Topishmoqlar");

        // Diamond info toast on Header click
        View headerBar = findViewById(R.id.headerBar);
        if (headerBar != null) {
            headerBar.setOnClickListener(v -> Toast.makeText(this,
                    "Sizning jami olmoslaringiz: " + userPreferences.getDiamonds() + " 💎",
                    Toast.LENGTH_SHORT).show());
        }
    }

    private void setupCategoryClick(View view, final int categoryIndex, final String categoryName) {
        if (view != null) {
            view.setOnClickListener(v -> launchCategoryQuiz(categoryIndex, categoryName));
        }
    }

    private void launchCategoryQuiz(int categoryIndex, String categoryName) {
        if (userPreferences != null) {
            userPreferences.addRecentCategory(categoryIndex);
        }
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("IS_RESUME", false);
        intent.putExtra("CATEGORY_INDEX", categoryIndex);
        intent.putExtra("CATEGORY_NAME", categoryName);
        startActivity(intent);
    }

    private void setupSearchFilter() {
        if (etSearch == null) return;
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterContent(s.toString().trim().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterContent(String query) {
        filterView(catLogic, query.isEmpty() || "mantiq".contains(query));
        filterView(catPuzzle, query.isEmpty() || "boshqotirma".contains(query));
        filterView(catWisdom, query.isEmpty() || "zukkolik".contains(query));
        filterView(catTopqirlik, query.isEmpty() || "topqirlik".contains(query));
        filterView(catRiddles, query.isEmpty() || "topishmoqlar".contains(query));

        List<Integer> recent = userPreferences != null ? userPreferences.getRecentCategories() : null;
        if (recent != null && recent.size() >= 3) {
            filterView(itemAct1, query.isEmpty() || matchesCategoryQuery(recent.get(0), query));
            filterView(itemAct2, query.isEmpty() || matchesCategoryQuery(recent.get(1), query));
            filterView(itemAct3, query.isEmpty() || matchesCategoryQuery(recent.get(2), query));
        }
    }

    private boolean matchesCategoryQuery(int categoryIndex, String query) {
        String name = getCategoryName(categoryIndex).toLowerCase();
        String title = getCategoryTitle(categoryIndex).toLowerCase();
        return name.contains(query) || title.contains(query);
    }

    private void filterView(View view, boolean show) {
        if (view != null) {
            view.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkSavedState();
        updateUserData();
    }

    private void updateUserData() {
        if (userPreferences != null) {
            if (tvUserScore != null) {
                tvUserScore.setText(String.valueOf(userPreferences.getDiamonds()));
            }

            List<Integer> recentList = userPreferences.getRecentCategories();
            if (!recentList.isEmpty()) {
                bindRecentItem(recentList.get(0), itemAct1, iconAct1, tvTitleAct1, scoreViewAct1);
            }
            if (recentList.size() >= 2) {
                bindRecentItem(recentList.get(1), itemAct2, iconAct2, tvTitleAct2, scoreViewAct2);
            }
            if (recentList.size() >= 3) {
                bindRecentItem(recentList.get(2), itemAct3, iconAct3, tvTitleAct3, scoreViewAct3);
            }
        }
    }

    private void bindRecentItem(int categoryIndex, View cardView, ImageView iconView, TextView titleView, CircularScoreView scoreView) {
        if (cardView == null) return;

        if (titleView != null) {
            titleView.setText(getCategoryTitle(categoryIndex));
        }
        if (iconView != null) {
            iconView.setImageResource(getCategoryIcon(categoryIndex));
            iconView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(this, getCategoryColor(categoryIndex))));
        }
        if (scoreView != null) {
            scoreView.setScore(userPreferences.getCategoryScore(categoryIndex, getDefaultScore(categoryIndex)), 10);
        }

        cardView.setOnClickListener(v -> launchCategoryQuiz(categoryIndex, getCategoryName(categoryIndex)));
    }

    private String getCategoryName(int index) {
        switch (index) {
            case 1: return "Boshqotirma";
            case 2: return "Zukkolik";
            case 3: return "Topqirlik";
            case 4: return "Topishmoqlar";
            default: return "Mantiq";
        }
    }

    private String getCategoryTitle(int index) {
        switch (index) {
            case 1: return "Boshqotirma va Diqqat";
            case 2: return "Zukkolik testlari";
            case 3: return "Topqirlik va Fikr";
            case 4: return "Qiziqarli Topishmoqlar";
            default: return "Mantiqiy savollar";
        }
    }

    private int getCategoryIcon(int index) {
        switch (index) {
            case 1: return R.drawable.ic_puzzle;
            case 2: return R.drawable.ic_lightbulb;
            case 3: return R.drawable.ic_trophy;
            case 4: return R.drawable.ic_brain;
            default: return R.drawable.ic_brain;
        }
    }

    private int getCategoryColor(int index) {
        switch (index) {
            case 1: return R.color.pink_badge;
            case 2: return R.color.gold_diamond;
            case 3: return R.color.green_badge;
            case 4: return R.color.purple_badge;
            default: return R.color.primary_blue;
        }
    }

    private int getDefaultScore(int index) {
        switch (index) {
            case 1: return 5;
            case 2: return 9;
            case 3: return 7;
            case 4: return 6;
            default: return 8;
        }
    }

    @Override
    public void startQuiz(boolean isResume) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("IS_RESUME", isResume);
        startActivity(intent);
    }

    @Override
    public void showResumeButton(boolean visible) {
        if (resumeCard != null) {
            resumeCard.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}