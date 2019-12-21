/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2017  Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.extension.frontend.client.widget.messaging;

import com.google.gwt.user.client.ui.Composite;
import com.openkm.extension.frontend.client.widget.messaging.detail.Detail;
import com.openkm.extension.frontend.client.widget.messaging.list.Message;
import com.openkm.frontend.client.extension.comunicator.UtilComunicator;
import com.openkm.frontend.client.panel.center.VerticalResizeHandler;
import com.openkm.frontend.client.panel.center.VerticalSplitLayoutExtended;

/**
 * Message Browser panel
 *
 * @author jllort
 *
 */
public class MessageBrowser extends Composite {

	private final static int IE_SIZE_RECTIFICATION = (UtilComunicator.getUserAgent().startsWith("ie") ? 2 : 0);
	public final static int SPLITTER_HEIGHT = 10;

	private VerticalSplitLayoutExtended verticalSplitLayoutPanel;

	public Message message;
	public Detail messageDetail;

	public int width = 0;
	public int height = 0;
	public int topHeight = 0;
	public int bottomHeight = 0;

	/**
	 * MessageBrowser
	 */
	public MessageBrowser() {
		verticalSplitLayoutPanel = new VerticalSplitLayoutExtended(new VerticalResizeHandler() {
			@Override
			public void onResize(int topHeight, int bottomHeight) {
				resizePanels();
			}
		});
		message = new Message();
		messageDetail = new Detail();
		verticalSplitLayoutPanel.getSplitPanel().addNorth(message, 300);
		verticalSplitLayoutPanel.getSplitPanel().add(messageDetail);

		messageDetail.setStyleName("okm-Left-Top-Border");
		messageDetail.addStyleName("okm-Mail-White");
		initWidget(verticalSplitLayoutPanel);
	}

	/**
	 * Refresh language values
	 */
	public void langRefresh() {
		message.langRefresh();
		messageDetail.langRefresh();
	}

	/**
	 * Sets the size on initialization
	 *
	 * @param width The max width of the widget
	 * @param height The max height of the widget
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		topHeight = (height - SPLITTER_HEIGHT) / 2;
		bottomHeight = height - (topHeight + SPLITTER_HEIGHT);
		verticalSplitLayoutPanel.setSize("" + width + "px", "" + height + "px");
		verticalSplitLayoutPanel.setSplitPosition(message, topHeight, false);
		resize();
	}

	private void resize() {
		verticalSplitLayoutPanel.setWidth("" + width + "px");
		message.setSize("" + width + "px", "" + topHeight + "px");
		// We substract 2 pixels for width and heigh generated by border line ( with IE add 2 px )
		message.table.setPixelSize(width - 2 + IE_SIZE_RECTIFICATION, topHeight - 2 + IE_SIZE_RECTIFICATION);
		message.table.fillWidth();
		// Resize the scroll panel on tab properties 
		// We substract 2 pixels for width and heigh generated by border line
		messageDetail.setPixelSize(width - 2, bottomHeight - 2);
	}


	/**
	 * Sets the panel width on resizing
	 */
	private void resizePanels() {
		topHeight = verticalSplitLayoutPanel.getTopHeight();
		bottomHeight = verticalSplitLayoutPanel.getBottomHeight();
		resize();
	}

	/**
	 * setWidth
	 */
	public void setWidth(int width) {
		this.width = width;
		resize();
	}
}