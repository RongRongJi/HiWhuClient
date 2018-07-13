package com.hiwhuUI.Activity.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hiwhu.hiwhuclient.R;

import java.util.List;

import entity.CommentCard;
import entity.ReplyCard;

public class ExpandAdapter_Comment extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandAdapter_Comment";
    private List<CommentCard> commentCardList;
    private Context context;

    public ExpandAdapter_Comment(Context context, List<CommentCard> commentCardList) {
        this.context = context;
        this.commentCardList = commentCardList;
    }

    private class GroupHolder {
        private ImageView user_logo;
        private TextView user_name, user_content, user_time;

        public GroupHolder(View view) {
            user_logo = (ImageView) view.findViewById(R.id.user_logo);
            user_content = (TextView) view.findViewById(R.id.user_content);
            user_name = (TextView) view.findViewById(R.id.user_name);
            user_time = (TextView) view.findViewById(R.id.user_time);
        }
    }

    private class ChildHolder {
        private LinearLayout reply_container;
        private TextView reply_name, reply_content, reply_time;

        public ChildHolder(View view) {
            reply_container = (LinearLayout) view.findViewById(R.id.reply_container);
            reply_name = (TextView) view.findViewById(R.id.reply_name);
            reply_content = (TextView) view.findViewById(R.id.reply_content);
            reply_time = (TextView) view.findViewById(R.id.reply_time);
        }
    }

    @Override
    public int getGroupCount() {
        return commentCardList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (commentCardList.get(i).getReplyCardList() == null) {
            return 0;
        } else {
            return commentCardList.get(i).getReplyCardList().size();
        }
    }

    @Override
    public CommentCard getGroup(int i) {
        return commentCardList.get(i);
    }

    @Override
    public ReplyCard getChild(int i, int i1) {
        return commentCardList.get(i).getReplyCardList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if (convertView != null) {
            groupHolder = (GroupHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_comment, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }

        Glide.with(context).load(R.drawable.stu_data_head)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.drawable.user)
                .centerCrop()
                .into(groupHolder.user_logo);

        groupHolder.user_name.setText(commentCardList.get(groupPosition).getName());
        groupHolder.user_time.setText(commentCardList.get(groupPosition).getTime());
        groupHolder.user_content.setText(commentCardList.get(groupPosition).getContent());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if (convertView != null) {
            childHolder = (ChildHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.reply_comment, viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }

        List<ReplyCard> childCardList = commentCardList.get(groupPosition).getReplyCardList();
        if (childCardList == null) {
            childHolder.reply_container.setVisibility(View.GONE);
            return convertView;
        } else {
            childHolder.reply_name.setText(childCardList.get(childPosition).getReply_name());
            childHolder.reply_content.setText(childCardList.get(childPosition).getReply_content());
            childHolder.reply_time.setText(childCardList.get(childPosition).getReply_time());

            return convertView;
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}