package pack;

import javax.persistence.*;

@Entity
public class Avis {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Activite act;
	/** Destinataire de l'avis **/
	@Column
	private String sujet;
	
	/** Type de destinataire **/
	private enum TypeDest { 
		LOGEMENT,PROFESSIONNEL,ACTIVITE;
	}
	@Column
	private TypeDest type;
	
	/** Note **/
	@Column
	private int note;
	
	/** Commentaire **/
	@Column
	private String review;
	
	@ManyToOne
	private Professionnel pro;
	
	public Avis(){};
	public void setSujet(String sujet){
		this.sujet = sujet;
	}
	public String getSujet(){
		return this.sujet;
	}
	public void setType(TypeDest type){
		this.type = type;
	}
	public TypeDest getType(){
		return this.type;
	}
	public void setNote(int note){
		this.note = note;
	}
	public int getNote(){
		return this.note;
	}
	public void setRevieww(String review){
		this.review = review;
	}
	public String getReview(){
		return this.review;
	}
}