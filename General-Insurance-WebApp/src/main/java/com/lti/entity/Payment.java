package com.lti.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Payment {

	@Id
	@SequenceGenerator(name = "PaymentSeqGen", sequenceName = "paymentSeq", initialValue = 1001, allocationSize = 13)
	@GeneratedValue(generator = "PaymentSeqGen")
	private int payment_id;
	private String cardHolderName;
	private String expiryDateOfCard;
	private double payment_amt;
	private long card_no;
	private int cvv;

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getExpiryDateOfCard() {
		return expiryDateOfCard;
	}

	public void setExpiryDateOfCard(String expiryDateOfCard) {
		this.expiryDateOfCard = expiryDateOfCard;
	}

	public double getPayment_amt() {
		return payment_amt;
	}

	public void setPayment_amt(double payment_amt) {
		this.payment_amt = payment_amt;
	}

	public long getCard_no() {
		return card_no;
	}

	public void setCard_no(long card_no) {
		this.card_no = card_no;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

}
