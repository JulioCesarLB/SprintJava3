package br.com.fiap.sprint3;

import java.util.*;

import javax.swing.*;

public class Sprint {

	public static void main(String[] args) {
		int teams;
		do {
			teams = Integer.parseInt(JOptionPane.showInputDialog(null,
					"Digite a quantidade de equipes que participaram do evento (até 88 equipes):"));
			if (teams < 1) {
				JOptionPane.showMessageDialog(null, teams + " é menor que o limite (1) \n Tente novamente!!");
			} else if (teams > 88) {

				JOptionPane.showMessageDialog(null, teams + " é maior que o limite (88) \n Tente novamente!!");
			}
		} while (teams < 1 && teams > 88);

		int combat = teams - 1;

		Object[][] tabela = new Object[teams][combat + 3];

////////////// | n da equipe | combate1 | combate2 | combate3 | notaTotal |
////////////// notaDesign
////////////// |-------------|----------|----------|----------|-----------|------------
//////// equipe|

		int[] score = new int[teams];
		int[] nota = new int[teams];

		tabela = ordenaTabela(insertData(combat, teams), teams, combat);
		mostraTabela(tabela, combat, teams);
		
		JOptionPane.showMessageDialog(null,"Hora de conferir os três primeiros");
		primeiros(tabela);

	}

	public static Object[][] insertData(int combat, int teams) {
		Object[][] tabela = new Object[teams][combat + 3];
		boolean verificacao;

		for (int i = 0; i < teams; i++) {

			int result;

// insere o número da equipe
			do {
				result = Integer.parseInt(
						JOptionPane.showInputDialog(null, "Digite o número da " + (i + 1) + "º equipe (11 até 99)"));

				verificacao = verificaNum(result, teams, tabela);

				if (result < 11 || result > 99) {
					JOptionPane.showMessageDialog(null, "Valor inválido");
				} else if (verificacao == false) {
					JOptionPane.showMessageDialog(null, "Número já utilizado");
				}

			} while ((result < 11 || result > 99) || verificacao == false);
			tabela[i][0] = result;

// Insere o valor da nota de design
			do {
				result = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o valor da nota de Design"));

				if (result < 0 || result > 10) {
					JOptionPane.showMessageDialog(null, "Valor inválido");
				}

			} while (result < 0 || result > 10);
			tabela[i][combat + 2] = result;
		}
		
		combates(tabela,teams,combat,1);
		
		return tabela;
	}
	public static void combates(Object[][] tabela, int teams, int combat,int situacao ) {
		Random random = new Random();

		for (int i = 0; i < teams; i++) {

			for (int k = i + 1; k <= combat; k++) {
				int valor = random.nextInt(10)+1;
				int valorRival = random.nextInt(10)+1;

				if (valor == valorRival) {
					JOptionPane.showMessageDialog(null, "O robô "+tabela[i][0] + " empatou com o robõ " + tabela[k][0] + " \ncom " + valor + " pontos de ataque");
					tabela[i][k] = "E";
					tabela[k][(i + 1)] = "E";

				} else if (valor > valorRival) {
					JOptionPane.showMessageDialog(null,"O robô "+tabela[i][0] + " derrotou o robõ " + tabela[k][0] + " \ncom " + valor + " pontos de ataque");
					tabela[i][k] = "V";
					tabela[k][(i + 1)] = "D";
				} else {
					JOptionPane.showMessageDialog(null,"O robô "+tabela[i][0] + " perdeu para o robõ " + tabela[k][0] + " \ncom apenas " + valor + " pontos de ataque");
					tabela[i][k] = "D";
					
					tabela[k][(i + 1)] = "V";
				}


			}

		}

///Insere os valores dos combates
		for (int i = 0; i < teams; i++) {
			int notaTotal = 0;

			for (int k = 1; k <= combat; k++) {

				char result = ((String) tabela[i][k]).charAt(0);

// calcula a quantidade de pontos por combate
				
				if(situacao==1) {
				
				if (result == 'V') {
					notaTotal = notaTotal + 5;

				} else if (result == 'E') {
					notaTotal = notaTotal + 3;
				} else if (result == 'D') {
				}
				}else {
					if (result == 'V') {
						notaTotal = notaTotal + 2;

					} else if (result == 'E') {
						notaTotal = notaTotal + 1;
					} else if (result == 'D') {
					}
				}
////// Insere a nota total

}
			tabela[i][(combat + 1)] = notaTotal;
		}

	}

	public static void mostraTabela(Object[][] tabela, int combat, int teams) {
		System.out.print("| n da equipe |");
		for (int i = 1; i <= combat; i++) {
			System.out.print("   combate " + i + " |");
		}

		System.out.print("  notaTotal   |  notaDesign |");
		System.out.println("");

		for (int i = 0; i < teams; i++) {
			for (int j = 0; j < combat + 3; j++) {
				System.out.print("      " + tabela[i][j] + "      |");
			}
			System.out.println("");
		}

	}

	public static Object[][] ordenaTabela(Object[][] tabela, int teams, int combat) {

		Object[][] ordenada = new Object[teams][combat + 3];

		for (int j = 0; j < teams; j++) {

			int index = retornaIndexMaior(tabela, teams, combat);

			for (int i = 0; i < combat + 3; i++) {
				ordenada[j][i] = tabela[index][i];
			}
			tabela[index][(combat + 1)] = -1;
		}
		return ordenada;
	}

	public static int retornaIndexMaior(Object[][] tabela, int teams, int combat) {

		int valor = -2, index = 0;
		for (int i = 0; i < teams; i++) {

			if (valor < (int) (tabela[i][(combat + 1)])) {
				valor = (int) (tabela[i][(combat + 1)]);
				index = i;

			} else if ((valor == (int) (tabela[i][(combat + 1)]))) {

				if ((int) tabela[index][combat + 2] > (int) (tabela[i][(combat + 2)])) {

				} else if ((int) tabela[index][combat + 2] < (int) (tabela[i][(combat + 2)])) {
					valor = (int) (tabela[i][(combat + 1)]);
					index = i;
				} else {

				}
			}
		}

		return index;
	}

	public static boolean verificaNum(int result, int teams, Object[][] tabela) {
		for (int i = 0; i < teams; i++) {
			if (tabela[i][0] == null) {
				return true;
			} else {
				if (result == (int) tabela[i][0]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void primeiros(Object[][] tabela) {
		Object[][] primeiros = new Object[3][5];
		
		for(int i =0; i<3;i++) {
			for(int j=0;j<5; j++) {
				if(j<4) {
				primeiros[i][j] = tabela[i][j];
				}else {
					primeiros[i][j] = tabela[i][((tabela[0].length)-1)];
				}
			}
		}
		
		JOptionPane.showMessageDialog(null, "Os três primeiros são:"
				+ "\n Número:"+primeiros[0][0]
				+ "\n Número:"+primeiros[1][0]
				+ "\n Número:"+primeiros[2][0]);
		
		combates(primeiros,3,2,2);
		
		System.out.println("");
		System.out.println("FIM DO TORNEIO:");
		mostraTabela(ordenaTabela(primeiros,3,2),2,3);
		
	}

}