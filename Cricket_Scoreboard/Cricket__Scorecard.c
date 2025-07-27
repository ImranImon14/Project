#include<stdio.h>
#include<stdlib.h>

//Global variable declared..........................
#define max 11;
int c;
int d;
int z=0;
int overs;
int batsman=max;
int bowlers;
int allruns=0;
int runs[100];
int balls[500];
int wicket[100];
int ballsbowler[500];
int allballs=0;
int netrun=0;
int netballs=0;
int netwicket=0;
int netovers=0;
int c1;
int d1;
int overs1;
int bowlers1;
int allruns1=0;
int runs1[100];
int balls1[500];
int wicket1[100];
int ballsbowler1[500];
int allballs1=0;
int netrun1=0;
int netballs1=0;
int netwicket1=0;
int netovers1=0;
int runsconcede[500];
int runsconcede1[500];

//Recording batsman and bowlers details for team 1...............................
void bat1(int over)
{
    printf("\nPlease enter the required details of the batsman and bowlers of team 1.\n\n");
    printf("There are %d batsman.\n",batsman);
co:
    {
        printf("\nEnter the number of bowlers for others team : ");
        scanf("%d",&bowlers);
    }
    if(over>1)
    {
        if(bowlers<=1)
        {
            printf("\nYour bowlers number must be greater than 1.\n");
            goto co;
        }
    }
    if(bowlers<=0)
    {
        printf("\nBowlers must be greater than 0.\n");
        goto co;
    }
    printf("\n");
    int j,recent1=1,recent2=2;
    for(int k=1; k<=batsman; k++)
        runs[k]=0;
    for(int l=1; l<=over*6; l++)
        balls[l]=0;
    for(int m=1; m<=bowlers; m++)
        wicket[m]=0;
    for(int n=1; n<=bowlers; n++)
        ballsbowler[n]=0;
    for(int p=1; p<=bowlers; p++)
        runsconcede[p]=0;
    int i=1,overs=0;
    while(i<=over*6)
    {
        if(z>0)
        {
            if(allruns>allruns1)
                break;
        }
        if(netwicket==batsman-1)
            break;
        int ball=0;
go:
        {
ob:
            {
                printf("\nEnter the bowler who will run the over : ");
                scanf("%d",&c);
            }
            if(overs==0)
                d=c;
            else if(overs>0&&c>0&&c<=bowlers)
            {
                if(c==d)
                {
                    printf("\nThis bowler ran an over recently.\nPlease choose again.\n");
                    goto ob;
                }
                else
                    d=c;
            }
        }
        if(c>0&&c<=bowlers)
        {
            //int n=6;
            printf("\nEnter 1 to 6 for a valid score and any other for a wicket.\n\n");
            for(j=1; j<=6; j++)
            {

                i++;
                printf("Runs scored by batsman %d at ball %d : ",recent1,j);
                int score;
                scanf("%d",&score);

                if(score>=0&&score<=6&&score%2==0)
                {
                    allruns=allruns+score;
                    runs[recent1]=runs[recent1]+score;
                    balls[recent1]=balls[recent1]+1;
                    runsconcede[c]=runsconcede[c]+score;
                }
                else if(score>=0&&score<=6&&score%2!=0)
                {
                    allruns=allruns+score;
                    runs[recent1]=runs[recent1]+score;
                    balls[recent1]=balls[recent1]+1;
                    runsconcede[c]=runsconcede[c]+score;
                    int temp=recent1;
                    recent1=recent2;
                    recent2=temp;
                }
                else if(score<0||score>6)
                {
                    netwicket=netwicket+1;
                    wicket[c]=wicket[c]+1;
                    runs[recent1] = runs[recent1] + 0;
                    balls[recent1] = balls[recent1] + 1;
                    runsconcede[c]=runsconcede[c]+0;
                    recent1++;
                    while(recent1<=recent2)
                    {
                        recent1++;
                    }
                }

                ball++;
                if(j==6)
                    overs++;
                printf(" Total balls of this over         Total runs                       Total overs                  Total wickets\n");
                printf("%7d%30d%35.1f %30d\n",ball,allruns,overs+0.1*(ball%6),netwicket);
                if(netwicket==batsman-1)
                    break;
                if(z>0)
                {
                    if(allruns>allruns1)
                        break;
                }
            }
            ballsbowler[c]=ballsbowler[c]+ball;
        }
        else
        {
            printf("\nBowler number must be among 1 to %d.\nPlease choose again.\n\n",bowlers);
            goto go;
        }
    }
    printf("\n");
}


//Recording batsman and bowlers details for team 2...............................
void bat2(int over)
{
    printf("\nPlease enter the required details of the batsman and bowlers of team 2.\n\n");
    printf("There are %d batsman.\n",batsman);
fo:
    {
        printf("\nEnter the number of bowlers for others team: ");
        scanf("%d",&bowlers1);
    }
    if(over>1)
    {
        if(bowlers1<=1)
        {
            printf("\nYour bowlers number must be greater than 1.\n");
            goto fo;
        }
    }
    if(bowlers1<=0)
    {
        printf("\nBowlers must be greater than 0.\n");
        goto fo;
    }
    printf("\n");
    int j,recent1=1,recent2=2;
    for(int k=1; k<=batsman; k++)
        runs1[k]=0;
    for(int l=1; l<=over*6; l++)
        balls1[l]=0;
    for(int m=1; m<=bowlers1; m++)
        wicket1[m]=0;
    for(int n=1; n<=bowlers1; n++)
        ballsbowler1[n]=0;
    for(int p=1; p<=bowlers1; p++)
        runsconcede1[p]=0;
    int i=1,overs1=0;
    while(i<=over*6)
    {
        if(z>0)
        {
            if(allruns<allruns1)
                break;
        }
        if(netwicket1==batsman-1)
            break;
        int ball=0;
go:
        {
ob:
            {
                printf("\nEnter the bowler who will run the over : ");
                scanf("%d",&c);
            }
            if(overs1==0)
                d=c;
            else if(overs1>0&&c>0&&c<=bowlers1)
            {
                if(c==d)
                {
                    printf("\nThis bowler ran an over recently.\nPlease choose again.\n");
                    goto ob;
                }
                else
                    d=c;
            }
        }
        if(c>0&&c<=bowlers1)
        {
            int n=6;
            printf("\nEnter 1 to 6 for a valid score and any other for a wicket.\n\n");
            for(j=1; j<=6; j++)
            {

                i++;
                printf("Runs scored by batsman %d at ball %d : ",recent1,j);
                int score;
                scanf("%d",&score);

                if(score>=0&&score<=6&&score%2==0)
                {
                    allruns1=allruns1+score;
                    runs1[recent1]=runs1[recent1]+score;
                    balls1[recent1]=balls1[recent1]+1;
                    runsconcede1[c]=runsconcede1[c]+score;
                }
                else if(score>=0&&score<=6&&score%2!=0)
                {
                    allruns1=allruns1+score;
                    runs1[recent1]=runs1[recent1]+score;
                    balls1[recent1]=balls1[recent1]+1;
                    runsconcede1[c]=runsconcede1[c]+score;
                    int temp=recent1;
                    recent1=recent2;
                    recent2=temp;
                }
                else if(score<0||score>6)
                {
                    netwicket1=netwicket1+1;
                    wicket1[c]=wicket1[c]+1;
                    runs1[recent1] = runs1[recent1] + 0;
                    balls1[recent1] = balls1[recent1] + 1;
                    runsconcede1[c]=runsconcede1[c]+0;
                    recent1++;
                    while(recent1<=recent2)
                    {
                        recent1++;
                    }
                }

                ball++;
                if(j==6)
                    overs1++;
                printf(" Total balls of this over         Total runs                       Total overs                  Total wickets\n");
                printf("%7d%30d%35.1f%30d\n",ball,allruns1,overs1+0.1*(ball%6),netwicket1);
                if(netwicket1==batsman-1)
                    break;
                if(z>0)
                {
                    if(allruns<allruns1)
                        break;
                }
            }
            ballsbowler1[c]=ballsbowler1[c]+ball;
        }
        else
        {
            printf("\nBowler number must be among 1 to %d.\nPlease choose again.\n\n",bowlers1);
            goto go;
        }
    }
    printf("\n");
}

//starting main function.....
int main()
{
    printf("                                               CRICKET SCOREBOARD\n\n");
    printf("How many overs for match : ");
    int x,choice,N;
    scanf("%d",&x);
ok:
    {
        printf("\n\nThere are two teams in a match.\nChoose which team will play the first inning.\n\nEnter 1 for team 1.\nOr enter 2 for team 2 : ");
        scanf("%d",&choice);
    }
    if(choice==1)
    {
        //N=choice;
        printf("\nFirst inning starts.\n");
        bat1(x);  //Calling function for batsman and bowler details......
        z++;
        printf("\nSecond inning starts\n");
        bat2(x);  //same....
    }
    else if(choice==2)
    {
        //N=choice;
        printf("\nFirst inning starts.\n");
        bat2(x);  //Calling function for batsman and bowler details......
        z++;
        printf("\nSecond inning starts.\n");
        bat1(x);  //same....
    }
    else
    {
        printf("\nSorry you have chosen the wrong key.\nChoice again\n");
        goto ok;
    }
    while(1)
    {
        printf("\n\n\nChoose 1 for batsman details.\nChoose 2 for bowler details.\nChoose 3 for match summary.\nChoose 4 for match result & exit.\nChoose one : ");
        int choose;
        scanf("%d",&choose);
        int b,bo,t1,t2,t3;
        switch(choose)
        {
        case 1:
        {
xo:
            {
                printf("Which team batsman details you wanna see : ");
                scanf("%d",&t1);
                printf("\n");
            }
            if(t1==1)
            {
go:
                {
                    printf("\nWhich batsman details you want to see : ");
                    scanf("%d",&b);
                    printf("\n");
                }
                if(b<=batsman&&b>0)
                {
                    printf("Batsman no                Batsman run               Ball played                Strike rate\n");
                    printf("%7d%25d%25d   ",b,runs[b],balls[b]);
                    if (balls[b] > 0)
                    {
                        printf("%30.4f\n", (runs[b] / (float)balls[b]) * 100);
                    }
                    else
                    {
                        printf("                       0.0000\n");
                    }
                }
                else
                {
                    printf("Wrong key.\nChoose again.\n\n");
                    goto go;
                }
            }
            else if(t1==2)
            {
wo:
                {
                    printf("\nWhich batsman details you want to see : ");
                    scanf("%d",&b);
                    printf("\n");
                }
                if(b<=batsman&&b>0)
                {
                    printf("Batsman no                Batsman run               Ball played                Strike rate\n");
                    printf("%7d%25d%25d",b,runs1[b],balls1[b],(runs1[b]/(float)balls1[b])*100);
                    if (balls[b] > 0)
                    {
                        printf("%30.4f\n", (runs1[b] / (float)balls1[b]) * 100);
                    }
                    else
                    {
                        printf("                       0.0000\n");
                    }
                }
                else
                {
                    printf("Wrong key.\nChoose again.\n\n");
                    goto wo;
                }
            }
            else
            {
                printf("\nSorry you have chosen a wrong key.\nSelect 1 or 2.\n\n");
                goto xo;
            }

            break;
        }
        case 2:
        {
ot:
            {
                printf("Which team bowler details you wanna see ? : ");
                scanf("%d",&t2);
                printf("\n");
            }
            if(t2==2)
            {
to:
                {
                    printf("Which bowler details you want to see : ");
                    scanf("%d",&bo);
                    printf("\n");
                }
                if(bo<=bowlers&&bo>0)
                {
                    printf("Bowler no                 Over taken                  runs                  wicket taken\n");
                    printf("%7d%25.1f%25d%25d\n",bo,(ballsbowler[bo]/6)+(float)0.1*(ballsbowler[bo]%6),runsconcede[bo],wicket[bo]);
                }
                else
                {
                    printf("Wrong key.\nChoose again.\n\n");
                    goto to;
                }
            }
            else if(t2==1)
            {
ow:
                {
                    printf("Which bowler details you want to see : ");
                    scanf("%d",&bo);
                    printf("\n");
                }
                if(bo<=bowlers1&&bo>0)
                {
                    printf("Bowler no                 Over taken                  runs                  Wicket taken\n");
                    printf("%7d%25.1f%25d%25d\n",bo,(ballsbowler1[bo]/6)+(float)0.1*(ballsbowler1[bo]%6),runsconcede1[bo],wicket1[bo]);
                }
                else
                {
                    printf("Wrong key.\nChoose again.\n\n");
                    goto ow;
                }
            }
            else
            {
                printf("\nSorry you have chosen a wrong key.\nChoose 1 or 2.\n\n");
                goto ot;
            }
            break;
        }
        case 3:
        {
            netrun=0;
            netrun1=0;
            for(int i=1; i<=batsman; i++)
            {
                netrun=netrun+runs[i];
                netrun1=netrun1+runs1[i];
            }
            for(int i=1; i<=bowlers; i++)
            {
                netovers=netovers+ballsbowler[i];
                netovers1=netovers1+ballsbowler1[i];
            }
            int s,s1;
            float tover,tover1,t,t4;
            s=netovers/6;
            t=netovers%6;
            tover=s+0.1*t;
            s1=netovers1/6;
            t4=netovers1%6;
            tover1=s1+0.1*t4;

            printf(" \n\n\n\n                              MATCH SUMMARY  TEAM 1                                  \n");
            printf("\nTotal score                           : %d\n",netrun);
            printf("\nTotal wicket                          : %d\n",netwicket);
            printf("\nTotal overs                           :%.1f\n",tover);
            printf("\n\n                            ALL batsman detail(Team_1):\n");
            printf("Batsman no                Batsman run               Ball played                Strike rate\n");
            for(int i=1; i<=batsman; i++)
            {
                printf("%7d%25d%25d",i,runs[i],balls[i]);
                if (balls[i] > 0)
                {
                    printf("%30.4f\n", (runs[i] / (float)balls[i]) * 100);
                }
                else
                {
                    printf("                      0.0000\n");
                }
            }
            printf("\n\n                         ALL bowlers detail(Team_2):\n");
            printf("Bowler no                 Over taken                     runs              Wicket taken \n");
            for(int i=1; i<=bowlers; i++)
            {
                printf("%7d%25.1f%25d%25d\n",i,(ballsbowler[i]/6)+(float)0.1*(ballsbowler[i]%6),runsconcede[i],wicket[i]);
            }


            printf(" \n\n\n\n\n                            MATCH SUMMARY  TEAM 2                                 \n");
            printf("\nTotal score                           : %d\n",netrun1);
            printf("\nTotal wicket                          : %d\n",netwicket1);
            printf("\nTotal overs                           :%.1f\n",tover1);
            printf("\n\n                            ALL batsman detail(Team_2):\n\n");
            printf("Batsman no                Batsman run               Ball played                Strike rate\n");
            for(int i=1; i<=batsman; i++)
            {
                printf("%7d%25d%25d",i,runs1[i],balls1[i]);
                if (balls1[i] > 0)
                {
                    printf("%30.4f\n", (runs1[i] / (float)balls1[i]) * 100);
                }
                else
                {
                    printf("                      0.0000\n");
                }
            }
            printf("\n\n                         ALL bowlers detail(Team_1):\n\n");
            printf("Bowler no                 Over taken                   runs                 Wicket taken \n");
            for(int i=1; i<=bowlers1; i++)
            {
                printf("%7d%25.1f%25d%25d\n",i,(ballsbowler1[i]/6)+(float)0.1*(ballsbowler1[i]%6),runsconcede1[i],wicket1[i]);
            }
            break;
        }
        case 4:
        {
            netrun=0;
            netrun1=0;
            for(int i=1; i<=batsman; i++)
            {
                netrun=netrun+runs[i];
                netrun1=netrun1+runs1[i];
            }
            if(netrun==netrun1)
                printf("\nMATCH TIED\n");
            else if(netrun>netrun1 && choice==1)
                printf("\n\nTeam_1 win by %d runs.\n",netrun-netrun1);
            else if(netrun1>netrun && choice==2)
                printf("\n\nTeam_2 win by %d runs.\n",netrun1-netrun);
            else if(netrun>netrun1 && choice==2)
                printf("\n\nTeam_1 win by %d wickets.\n",10-netwicket);
            else if(netrun1>netrun && choice==1)
                printf("\n\nTeam_2 win by %d wickets.\n",10-netwicket1);
            exit(1);
        }
        default:
        {
            printf("\nPlease choose a number from 1 to 4.\n");
            continue;
        }
        }
    }
}

