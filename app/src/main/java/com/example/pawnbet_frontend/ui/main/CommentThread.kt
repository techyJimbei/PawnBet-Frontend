package com.example.pawnbet_frontend.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pawnbet_frontend.model.CommentResponse
import com.example.pawnbet_frontend.ui.theme.DarkGrey
import com.example.pawnbet_frontend.ui.theme.LightGrey
import com.example.pawnbet_frontend.ui.theme.NavyBlue
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimeAgo(isoString: String?): String {
    return try {
        if (isoString.isNullOrBlank()) return ""
        val commentTime = LocalDateTime.parse(isoString)
        val now = OffsetDateTime.now()
        val duration = Duration.between(commentTime, now)

        val minutes = duration.toMinutes()
        val hours = duration.toHours()
        val days = duration.toDays()

        when {
            minutes < 1 -> "just now"
            minutes < 60 -> "$minutes min ago"
            hours < 24 -> "$hours hr ago"
            days < 7 -> "$days d ago"
            else -> commentTime.toLocalDate().toString()
        }
    } catch (e: Exception) {
        ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentThread(
    comments: List<CommentResponse>,
    onReply: (CommentResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        comments.forEach { comment ->
            CommentItem(
                comment = comment,
                onReply = onReply,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentItem(
    comment: CommentResponse,
    onReply: (CommentResponse) -> Unit,
    modifier: Modifier = Modifier,
    depth: Int = 0
) {
    Column(
        modifier = modifier
            .padding(start = (depth * 24).dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, color = LightGrey)
            .clip(shape = RoundedCornerShape(20.dp))
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            AsyncImage(
                model = comment.user?.profileImageUrl ?: "",
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = comment.user.username,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = NavyBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formatTimeAgo(comment?.createdAt),
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
                Text(
                    text = comment?.text ?: "",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        TextButton(
            onClick = { onReply(comment) },
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Reply", color = DarkGrey)
        }

        comment.replies.forEach { reply ->
            CommentItem(
                comment = reply,
                onReply = onReply,
                depth = depth + 1
            )
        }
    }
}
