package com.mertalptasdelen.quick;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Object;
import static java.lang.Math.toIntExact;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.vdurmont.emoji.EmojiParser;

public class Kaan extends TelegramLongPollingBot {

	List<String> itemList = new ArrayList<String>();
	String emoji = EmojiParser.parseToUnicode(":heart_eyes: :heart:");
	String unamusedEmoji = EmojiParser.parseToUnicode(":unamused:");
	String notBoughtEmoji = EmojiParser.parseToUnicode(":x:");
	String doneEmoji = EmojiParser.parseToUnicode(":heavy_check_mark:");
	String emergencyEmoji = EmojiParser.parseToUnicode(":rotating_light:");
	String confettiEmoji = EmojiParser.parseToUnicode(":confetti_ball:");

	@Override
	public void onUpdateReceived(Update update) {

		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {

			String user_first_name = update.getMessage().getFrom().getFirstName();
			String user_last_name = update.getMessage().getFrom().getLastName();
			long user_id = update.getMessage().getFrom().getId();
			String message_text = update.getMessage().getText();
			long chat_id = update.getMessage().getChatId();


			if (update.getMessage().getText().equals("/talk")) {
				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id).setText(
								"Mertalp ozlemi cok ama cook fazla seviyor bunu ben bile sana aciklamak istesem bir kac yil belkide daha fazla yazi yamam gerek"
										+ emoji);

				InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
				List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
				List<InlineKeyboardButton> rowInline = new ArrayList<>();
				rowInline.add(
						new InlineKeyboardButton().setText("Update message text").setCallbackData("update_msg_text"));
				// Set the keyboard to the markup
				rowsInline.add(rowInline);
				// Add it to the message
				markupInline.setKeyboard(rowsInline);
				message.setReplyMarkup(markupInline);
				log(user_first_name, user_last_name, Long.toString(user_id), message_text, Long.toString(chat_id));

				try {
					execute(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if (update.getMessage().getText().contains("/add")) {

				// Extracting the item name
				String addedItem = update.getMessage().getText();
				addedItem = addedItem.substring(5);

				// adding item to the list
				itemList.add(addedItem);

				SendMessage addingMessage = new SendMessage().setChatId(chat_id)
						.setText("You added the list " + addedItem);
				log(user_first_name, user_last_name, Long.toString(user_id), message_text, Long.toString(chat_id));

				try {
					execute(addingMessage); // Sending our message object to user
					for (int i = 0; i < itemList.size(); i++) {
						SendMessage indexedItem = new SendMessage().setChatId(chat_id)
								.setText(notBoughtEmoji + "  " + itemList.get(i));
						execute(indexedItem);
					}
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

			} else if (update.getMessage().getText().contains("/done")) {
				// Extracting the item name
				String receiptItem = update.getMessage().getText();
				receiptItem = receiptItem.substring(6);

				if (itemList.isEmpty()) {
					SendMessage warningMessage = new SendMessage().setChatId(chat_id)
							.setText("List is empty man. First try to add item to list. What do you say YO?");

					try {
						execute(warningMessage);
					} catch (TelegramApiException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				// Checking the item added on list or user just kidding us
				else if (!itemList.contains(receiptItem)) {

					SendMessage falseMessage = new SendMessage().setChatId(chat_id)
							.setText("Man are you okay ? Item never added on the list please..." + unamusedEmoji + "\n\nHere is the list, maybe you forgot\n");


					try {
						execute(falseMessage); // Sending our message object to user
						for (int i = 0; i < itemList.size(); i++) {
							SendMessage indexedItem = new SendMessage().setChatId(chat_id)
									.setText(notBoughtEmoji + "  " + itemList.get(i));
							execute(indexedItem);
						}
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				}

				else {
					SendMessage doneMessage = new SendMessage().setChatId(chat_id)
							.setText(confettiEmoji + "Oooh man at last you did something good" + confettiEmoji + "\n\n"
									+ doneEmoji + "  " + receiptItem.toUpperCase());

					itemList.remove(receiptItem);

					try {
						execute(doneMessage);
					} catch (TelegramApiException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}

			else if (update.getMessage().getText().contains("/list")) {

				if (itemList.isEmpty()) {
					SendMessage emptyListMessage = new SendMessage().setChatId(chat_id)
							.setText("List is currently empty. First try to add something meaningful.");
					try {
						execute(emptyListMessage);
					} catch (TelegramApiException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} else {
					try {
						for (int i = 0; i < itemList.size(); i++) {
							SendMessage indexedItem = new SendMessage().setChatId(chat_id)
									.setText(notBoughtEmoji + "  " + itemList.get(i).toUpperCase());
							execute(indexedItem);
						}
					} catch (TelegramApiException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

			}

		} else if (update.hasCallbackQuery()) {
			// Set variables
			String call_data = update.getCallbackQuery().getData();
			long message_id = update.getCallbackQuery().getMessage().getMessageId();
			long chat_id = update.getCallbackQuery().getMessage().getChatId();

			if (call_data.equals("update_msg_text")) {
				String answer = "Updated message text";
				EditMessageText new_message = new EditMessageText().setChatId(chat_id)
						.setMessageId(toIntExact(message_id)).setText(answer);
				try {
					execute(new_message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "Kaan";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "967179967:AAHAQeft84EhSFUwDbyeSbUzHQkmK8TIUH8";

	}

	private void log(String first_name, String last_name, String user_id, String txt, String chat_id) {
		System.out.println("\n ----------------------------");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		System.out
				.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt + "\n\n"+"and the chat id is" + chat_id);
	}

}
