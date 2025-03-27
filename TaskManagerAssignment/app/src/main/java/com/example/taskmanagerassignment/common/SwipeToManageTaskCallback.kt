import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.HapticFeedbackConstants
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagerassignment.R

class SwipeToManageTaskCallback(
    context: Context,
    private val onToggleComplete: (Int, Boolean) -> Unit,
    private val onDelete: (Int) -> Unit,
    private val onMove: (Int, Int) -> Unit
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    private val completeBackground = ColorDrawable(Color.GREEN)
    private val deleteBackground = ColorDrawable(Color.RED)
    private val completeIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_check)
    private val deleteIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_delete)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        viewHolder.itemView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        onMove(fromPosition, toPosition)
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            viewHolder?.itemView?.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        }
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val iconSize = (itemView.height * 0.5).toInt()
        val iconMargin = (itemView.height * 0.2).toInt()
        val threshold = itemView.width * 0.3

        if (dX > 0) {
            completeBackground.setBounds(
                itemView.left,
                itemView.top,
                itemView.left + dX.toInt(),
                itemView.bottom
            )
            completeBackground.draw(c)

            if (dX > threshold) {
                val iconTop = itemView.top + (itemView.height - iconSize) / 2
                val iconLeft = itemView.left + iconMargin
                val iconRight = iconLeft + iconSize

                completeIcon?.setBounds(iconLeft, iconTop, iconRight, iconTop + iconSize)
                completeIcon?.draw(c)
            }
        } else if (dX < 0) {
            deleteBackground.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            deleteBackground.draw(c)

            if (dX < -threshold) {
                val iconTop = itemView.top + (itemView.height - iconSize) / 2
                val iconRight = itemView.right - iconMargin
                val iconLeft = iconRight - iconSize

                deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconTop + iconSize)
                deleteIcon?.draw(c)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.RIGHT) {
            onToggleComplete(position, true)
        } else if (direction == ItemTouchHelper.LEFT) {
            onDelete(position)
        }
    }
}
