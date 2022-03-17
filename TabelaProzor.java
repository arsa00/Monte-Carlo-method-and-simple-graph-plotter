package zadatak;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.border.Border;

public class TabelaProzor extends Frame
{
	private ArrayList<Integer> n = new ArrayList<Integer>();
	private ArrayList<Double> Pn = new ArrayList<Double>();
	
	public TabelaProzor(ArrayList<Integer> nl, ArrayList<Double> pnl)
	{
		this.n = nl;
		this.Pn = pnl;
		setBounds(500, 200, 400, 500);
		setTitle("Tabela");
		setResizable(false);
		
		//Panel tabela = new Panel(new GridLayout(0, 2, 10, 15));
		TextArea tabela = new TextArea(0, 2);
		tabela.setEditable(false);
		tabela.append("n:");
		tabela.append("\t\t\t");
		tabela.append("Pn:");
		tabela.append("\n\n");
		for(int i = 0; i < n.size(); i++)
		{
			tabela.append((new StringBuilder().append(n.get(i))).toString());
			tabela.append("\t\t\t");
			tabela.append((new StringBuilder().append(Pn.get(i))).toString());
			tabela.append("\n");
			//tabela.add(new Label((new StringBuilder().append(n.get(i)).toString()), Label.CENTER));
			//tabela.add(new Label((new StringBuilder().append(Pn.get(i)).toString()), Label.CENTER));
		}
		add(tabela, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});
		
		setVisible(true);
	}
}
