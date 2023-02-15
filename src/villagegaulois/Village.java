package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalMax) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalMax);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private static class Marche{
		private Etal[] etal;
		
		private Marche(int nbEtal) {
			etal = new Etal[nbEtal];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etal.length) {
				if (!etal[indiceEtal].isEtalOccupe()) {
					etal[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
				};
			};
		}
		
		private int trouverEtalLibre() {
			int indiceEtalLibre = -1;
			int compteur = 0;
			while (etal[compteur].isEtalOccupe() && compteur < etal.length) {
				compteur ++;
			}
			if (!etal[compteur].isEtalOccupe()) indiceEtalLibre = compteur;
			return indiceEtalLibre;
		}
		
		private Etal[] trouverEtals(String produit) {
			int compteurEtalProduits = 0;
			int horreur = 0;
			for (int i = 0; i < etal.length; i++) {
				if (etal[i].contientProduit(produit)) compteurEtalProduits++;
			}
			Etal[] etalProduit = new Etal[compteurEtalProduits];
			for (int i = 0; i < etal.length; i++) {
				if (etal[i].contientProduit(produit)) {
					etalProduit[horreur] = etal[compteurEtalProduits];
				}
			}
			return etalProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalVendeur = null;
			int i = 0; 
			while( i < etal.length && etalVendeur == null) {
				if (etal[i].getVendeur() == gaulois) etalVendeur = etal[i];
				i++;
			}
			return etalVendeur;
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int etalVide = marche.trouverEtalLibre();
		marche.utiliserEtal(etalVide, vendeur, produit, nbProduit);
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " 
				+ nbProduit + " " + produit + ".");
		return chaine.toString();
	}
}