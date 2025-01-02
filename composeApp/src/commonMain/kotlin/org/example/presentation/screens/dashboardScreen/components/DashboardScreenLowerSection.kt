
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material.placeholder
import com.eygraber.compose.placeholder.shimmer
import org.example.domain.model.Transaction
import org.example.presentation.screens.dashboardScreen.components.TransactionItem

@Composable
fun DashboardScreenLowerSection(
    isTransactionsLoading: Boolean,
    scrollState: LazyListState,
    transactions: List<Transaction>,
) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = if (isTransactionsLoading) 16.dp else 0.dp)
                .placeholder(
                    visible = isTransactionsLoading, color = Color.Gray,
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White,
                    ),
                )
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(transactions.size) { index ->
                val transaction = transactions[index]
                TransactionItem(transaction = transaction)
            }
        }

}

